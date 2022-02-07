package top.reid.smart.db.util;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import top.reid.smart.core.util.StrTools;
import top.reid.smart.spring.SpringContextTools;
import top.reid.system.vo.SysPermissionDataRuleModel;
import top.reid.system.vo.SysUserCacheInfo;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 数据权限查询规则容器工具类
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/2/7
 * @Version V1.0
 **/
@Slf4j
public class DataAutorTools {
    /**
     * 菜单数据作者规则
     */
    public static final String MENU_DATA_AUTHOR_RULES = "MENU_DATA_AUTHOR_RULES";
    /**
     * 菜单数据作者规则 SQL
     */
    public static final String MENU_DATA_AUTHOR_RULE_SQL = "MENU_DATA_AUTHOR_RULE_SQL";
    /**
     * 系统用户信息
     */
    public static final String SYS_USER_INFO = "SYS_USER_INFO";
    /**
     * 当前系统数据库类型
     */
    private static final String DB_TYPE = "";
    private static DbType dbTypeEnum = null;

    /**
     * 往链接请求里面，传入数据查询条件
     *
     * @param request 请求
     * @param dataRules 菜单权限规则集合
     */
    public static synchronized void installDataSearchConditon(HttpServletRequest request, List<SysPermissionDataRuleModel> dataRules) {
        // 1.先从 request 获取 MENU_DATA_AUTHOR_RULES，如果存则获取到 LIST
        List<SysPermissionDataRuleModel> list = loadDataSearchConditon();
        if (list==null) {
            // 2.如果不存在，则new一个list
            list = new ArrayList<SysPermissionDataRuleModel>();
        }
        list.addAll(dataRules);
        // 3.往 list 里面增量存指
        request.setAttribute(MENU_DATA_AUTHOR_RULES, list);
    }

    /**
     * 获取请求对应的数据权限规则
     *
     * @return 菜单权限规则集合
     */
    @SuppressWarnings("unchecked")
    public static synchronized List<SysPermissionDataRuleModel> loadDataSearchConditon() {
        return (List<SysPermissionDataRuleModel>) SpringContextTools.getHttpServletRequest().getAttribute(MENU_DATA_AUTHOR_RULES);

    }

    /**
     * 获取请求对应的数据权限 SQL
     *
     * @return  SQL 字符串
     */
    public static synchronized String loadDataSearchConditonSQLString() {
        return (String) SpringContextTools.getHttpServletRequest().getAttribute(MENU_DATA_AUTHOR_RULE_SQL);
    }

    /**
     * 往链接请求里面，传入数据查询条件
     *
     * @param request 请求
     * @param sql sql
     */
    public static synchronized void installDataSearchConditon(HttpServletRequest request, String sql) {
        String ruleSql = (String)loadDataSearchConditonSQLString();
        if (!StringUtils.hasText(ruleSql)) {
            request.setAttribute(MENU_DATA_AUTHOR_RULE_SQL,sql);
        }
    }

    /**
     * 将用户信息存到 request
     * @param request 请求
     * @param userinfo 系统用户缓存信息
     */
    public static synchronized void installUserInfo(HttpServletRequest request, SysUserCacheInfo userinfo) {
        request.setAttribute(SYS_USER_INFO, userinfo);
    }

    /**
     * 将用户信息存到 request
     * @param userinfo 系统用户缓存信息
     */
    public static synchronized void installUserInfo(SysUserCacheInfo userinfo) {
        SpringContextTools.getHttpServletRequest().setAttribute(SYS_USER_INFO, userinfo);
    }

    /**
     * 从 request 获取用户信息
     * @return 系统用户缓存信息
     */
    public static synchronized SysUserCacheInfo loadUserInfo() {
        return (SysUserCacheInfo) SpringContextTools.getHttpServletRequest().getAttribute(SYS_USER_INFO);
    }

    /**
     * 全局获取平台数据库类型（对应 mybatisPlus 枚举）
     * @return 数据库类型
     */
    public static String getDatabaseType() {
        return Objects.requireNonNull(getDatabaseTypeEnum()).getDb();
    }

    /**
     * 全局获取平台数据库类型（对应 mybatisPlus 枚举）
     * @return 数据库类型
     */
    public static DbType getDatabaseTypeEnum() {
        if (ObjectUtil.isNotEmpty(dbTypeEnum)) {
            return dbTypeEnum;
        }
        try {
            DataSource dataSource = SpringContextTools.getApplicationContext().getBean(DataSource.class);
            dbTypeEnum = JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL());
            return dbTypeEnum;
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }
}
