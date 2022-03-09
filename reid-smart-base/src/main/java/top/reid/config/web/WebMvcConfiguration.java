package top.reid.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.smart.core.util.StrTools;

import java.util.List;

/**
 * <p>
 * Spring Boot 解决跨域问题
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/14
 * @Version V1.0
 **/
@Configuration
public class WebMvcConfiguration extends WebProperties implements WebMvcConfigurer {

    /**
     * 静态资源的配置 - 使得可以从磁盘中读取 Html、图片、视频、音频等
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**");
        if(StrTools.isNotEmpty(this.getStaticFiles())) {
            String[] fileUrl = this.getStaticFiles().split(CommonCharacter.COMMA);
            for(String url : fileUrl) {
                if (StrTools.isNotEmpty(url = url.trim())) {
                    registry.addResourceHandler(url+ "//");
                }
            }
        }
        if(StrTools.isNotEmpty(this.getStaticFiles())) {
            String[] classpathUrl = this.getStaticClasspath().split(CommonCharacter.COMMA);
            for(String url : classpathUrl) {
                if (StrTools.isNotEmpty(url = url.trim())) {
                    registry.addResourceHandler(url);
                }
            }
        }
    }

    /**
     * 方案一： 默认访问根路径跳转 doc.html 页面 （ swagger 文档页面）<br>
     * 方案二： 访问根路径改成跳转 index.html 页面 （简化部署方案： 可以把前端打包直接放到项目的 webapp，上面的配置）<br>
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("doc.html");
    }

    @Bean
    @Conditional(CorsFilterCondition.class)
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 是否允许请求带有验证信息
        corsConfiguration.setAllowCredentials(true);
        // 允许访问的客户端域名
        corsConfiguration.addAllowedOrigin("*");
        // 允许服务端访问的客户端请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许访问的方法名,GET POST等
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 添加 Long 转 json 精度丢失的配置
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

}
