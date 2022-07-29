package top.reid.smart.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.*;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import top.reid.smart.config.shiro.filters.CustomShiroFilterFactoryBean;
import top.reid.smart.config.shiro.filters.JwtFilter;
import top.reid.smart.config.shiro.filters.KickoutSessionControlFilter;
import top.reid.smart.base.core.map.MapTools;
import top.reid.smart.base.core.util.CommonCharacter;
import top.reid.smart.base.core.util.StrTools;
import top.reid.smart.base.spring.annotation.ConditionalOnProperty;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.*;

/**
 * <p>
 * Shiro 配置类
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/13
 * @Version V1.0
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "reid.shiro", name = "enableShiro", havingValue = "true")
public class ShiroConfig extends ShiroProperties {

    @Resource
    private Environment env;

    @Resource
    LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * Filter Chain 定义说明
     *
     * 1、一个 URL 可以配置多个 Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如 perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("getSecurityManager") DefaultWebSecurityManager securityManager) {
        CustomShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 拦截器，anon 定义必须在 authc 之前
        // anon：无需认证就可以访问
        // authc：必须认证了才可以访问
        // user：拥有了对某个资源的权限才可以访问
        // perms: 拥有对某个资源的权限才能访问
        // role：拥有某个角色权限才能访问
        // 所有权限：package org.apache.shiro.web.filter.mgt.DefaultFilter
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        for(String url : this.getExcludeUrls()) {
            if (StrTools.isNotEmpty(url = url.trim())) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }

        // 添加自己的过滤器并且取名为 jwt
        Map<String, Filter> filterMap = MapTools.newHashMap(1);
        // 如果 cloudServer 为空 则说明是单体 需要加载跨域配置【微服务跨域切换】
        Object cloudServer = env.getProperty(CommonCharacter.CLOUD_SERVER_KEY);
        filterMap.put("jwt", new JwtFilter(cloudServer == null));
        shiroFilterFactoryBean.setFilters(filterMap);
        // 过滤链定义，从上向下顺序执行，一般将/**放在最后
        filterChainDefinitionMap.put("/**", "jwt");

        // 未授权界面返回 JSON
        shiroFilterFactoryBean.setLoginUrl(this.getLoginUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(this.getUnauthorizedUrl());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置核心安全事务管理器
     */
    @Bean
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("myShiroRealm") ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);

        /*
         * 关闭 shiro 自带的 session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 自定义缓存实现,使用 redis
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 自定义 Realm 认证逻辑
     */
    @Bean
    public ShiroRealm myShiroRealm() {
        return new ShiroRealm();
    }

    /**
     * cacheManager 缓存 redis 实现
     */
    public RedisCacheManager redisCacheManager() {
        log.info("创建缓存管理器 RedisCacheManager ");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // redis 中针对不同用户缓存(此处的 id 需要对应 user 实体中的 id 字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName(this.getPrincipalIdFieldName());
        // 用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    /**
     * 配置 shiro redisManager
     */
    @Bean
    public IRedisManager redisManager() {
        log.info("创建 RedisManager，连接 Redis......");
        IRedisManager manager;
        // redis 单机支持
        if (lettuceConnectionFactory.getClusterConfiguration() == null || lettuceConnectionFactory.getClusterConfiguration().getClusterNodes().isEmpty()) {
            RedisManager redisManager = new RedisManager();
            redisManager.setHost(lettuceConnectionFactory.getHostName());
            redisManager.setDatabase(lettuceConnectionFactory.getDatabase());
            redisManager.setTimeout(0);
            if (!StrTools.isEmpty(lettuceConnectionFactory.getPassword())) {
                redisManager.setPassword(lettuceConnectionFactory.getPassword());
            }
            manager = redisManager;
        }else{
            // redis 集群支持，优先使用集群配置
            RedisClusterManager redisManager = new RedisClusterManager();
            Set<HostAndPort> portSet = new HashSet<>();
            lettuceConnectionFactory.getClusterConfiguration().getClusterNodes().forEach(node -> portSet.add(new HostAndPort(node.getHost() , node.getPort() == null ? 6379 : node.getPort())));
            if (StrTools.isNotEmpty(lettuceConnectionFactory.getPassword())) {
                JedisCluster jedisCluster = new JedisCluster(portSet, 2000, 2000, 5, lettuceConnectionFactory.getPassword(), new GenericObjectPoolConfig<>());
                redisManager.setPassword(lettuceConnectionFactory.getPassword());
                redisManager.setJedisCluster(jedisCluster);
            } else {
                JedisCluster jedisCluster = new JedisCluster(portSet);
                redisManager.setJedisCluster(jedisCluster);
            }
            manager = redisManager;
        }
        return manager;
    }

    /**
     * 会话管理器
     */
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(@Qualifier("redisSessionDAO") RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * 用于从 Redis 设置/获取认证信息
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(@Qualifier("redisManager") IRedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(@Qualifier("getDefaultWebSessionManager") DefaultWebSessionManager defaultWebSessionManager) {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(redisCacheManager());
        kickoutSessionControlFilter.setSessionManager(defaultWebSessionManager);
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);
        kickoutSessionControlFilter.setKickoutUrl(this.getKickoutUrl());
        return kickoutSessionControlFilter;
    }

    /**
     * 添加注解支持，授权所用配置
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用 cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        // 添加前缀判断 不匹配 任何 Advisor
        defaultAdvisorAutoProxyCreator.setUsePrefix(this.isUsePrefix());
        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix(this.getAdvisorBeanNamePrefix());
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 使授权注解起作用，不想配置可以在 pom 文件中加入
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("getSecurityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public Authorizer authorizer(){
        return new ModularRealmAuthorizer();
    }

}
