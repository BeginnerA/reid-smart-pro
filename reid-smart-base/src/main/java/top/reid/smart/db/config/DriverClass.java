package top.reid.smart.db.config;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 驱动类工具
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class DriverClass {
    private static final Logger logger = LoggerFactory.getLogger(DriverClass.class);

    /**
     * 数据库连接对象
     */
    private static volatile Connection CONNECTION = null;

    /**
     * 存放驱动类信息
     */
    private static final Map<String, String> DRIVER_CLASS_MAP;
    /**
     * 存放数据库类型
     */
    public static String DRIVER_CLASS_NAME = "mysql";


    static {
        DRIVER_CLASS_MAP = new HashMap<>(66);
        DRIVER_CLASS_MAP.put("jdbc:impala", "com.cloudera.impala.jdbc41.Driver");
        DRIVER_CLASS_MAP.put("jdbc:hive", "org.apache.hive.jdbc.HiveDriver");
        DRIVER_CLASS_MAP.put("jdbc:db2", "COM.ibm.db2.jdbc.app.DB2Driver");
        DRIVER_CLASS_MAP.put("jdbc:firebirdsql", "org.firebirdsql.jdbc.FBDriver");
        DRIVER_CLASS_MAP.put("jdbc:edbc", "ca.edbc.jdbc.EdbcDriver");
        DRIVER_CLASS_MAP.put("jdbc:pointbase", "com.pointbase.jdbc.jdbcUniversalDriver");
        DRIVER_CLASS_MAP.put("jdbc:fake", "com.alibaba.druid.mock.MockDriver");
        DRIVER_CLASS_MAP.put("jdbc:informix-sqli", "com.informix.jdbc.IfxDriver");
        DRIVER_CLASS_MAP.put("jdbc:sqlite", "org.sqlite.JDBC");
        DRIVER_CLASS_MAP.put("jdbc:microsoft", "com.microsoft.jdbc.sqlserver.SQLServerDriver");
        DRIVER_CLASS_MAP.put("jdbc:hsqldb", "org.hsqldb.jdbcDriver");
        DRIVER_CLASS_MAP.put("jdbc:postgresql", "org.postgresql.Driver");
        DRIVER_CLASS_MAP.put("jdbc:ingres", "com.ingres.jdbc.IngresDriver");
        DRIVER_CLASS_MAP.put("jdbc:cloudscape", "COM.cloudscape.core.JDBCDriver");
        DRIVER_CLASS_MAP.put("jdbc:JSQLConnect", "com.jnetdirect.jsql.JSQLDriver");
        DRIVER_CLASS_MAP.put("jdbc:derby", "org.apache.derby.jdbc.EmbeddedDriver");
        DRIVER_CLASS_MAP.put("jdbc:timesten", "com.timesten.jdbc.TimesTenDriver");
        DRIVER_CLASS_MAP.put("jdbc:interbase", "interbase.interclient.Driver");
        DRIVER_CLASS_MAP.put("jdbc:h2", "org.h2.Driver");
        DRIVER_CLASS_MAP.put("jdbc:as400", "com.ibm.as400.access.AS400JDBCDriver");
        DRIVER_CLASS_MAP.put("jdbc:sybase:Tds", "com.sybase.jdbc2.jdbc.SybDriver");
        DRIVER_CLASS_MAP.put("jdbc:mock", "com.alibaba.druid.mock.MockDriver");
        DRIVER_CLASS_MAP.put("jdbc:oracle", "oracle.jdbc.driver.OracleDriver");
        DRIVER_CLASS_MAP.put("jdbc:mysql", "com.mysql.cj.jdbc.Driver");
        DRIVER_CLASS_MAP.put("jdbc:odps", "com.aliyun.odps.jdbc.OdpsDriver");
        DRIVER_CLASS_MAP.put("jdbc:mckoi", "com.mckoi.JDBCDriver");
        DRIVER_CLASS_MAP.put("jdbc:jtds", "net.sourceforge.jtds.jdbc.Driver");
        DRIVER_CLASS_MAP.put("jdbc:sapdb", "com.sap.dbtech.jdbc.DriverSapDB");
        DRIVER_CLASS_MAP.put("jdbc:JTurbo", "com.newatlanta.jturbo.driver.Driver");
        DRIVER_CLASS_MAP.put("jdbc:mimer:multi1", "com.mimer.jdbc.Driver");
        DRIVER_CLASS_MAP.put("jdbc:ucanaccess", "net.ucanaccess.jdbc.UcanaccessDriver");
    }

    /**
     * 加载驱动
     * @param driverClass 驱动类
     * @param url 连接信息
     */
    public static void loadDriverClass(String driverClass, final String url) {
        if(StrUtil.isEmpty(driverClass)) {
            Assert.notEmpty(url, "url 是必须参数");
            driverClass = getDriverClassByUrl(url);
        }

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            logger.error("初始化失败：", e);
        }
    }

    /**
     * 获取连接对象
     * @param driverClass driver
     * @param url 连接地址
     * @param username 用户名
     * @param password 密码
     * @return 连接对象
     */
    public static Connection getConnection(String driverClass, String url, String username, String password) {
        if (CONNECTION == null) {
            synchronized (Connection.class) {
                if (CONNECTION == null) {
                    loadDriverClass(driverClass, url);
                    try {
                        CONNECTION = DriverManager.getConnection(url, username, password);
                    }catch (SQLException e) {
                        logger.error("数据库访问错误：", e);
                    }
                }
            }
        }
        return CONNECTION;
    }

    /**
     * 根据 URL 获取对应的驱动类
     *
     * 1. 禁止 url 为空
     * 2. 如果未找到，则直接报错。
     * @param url url
     * @return 驱动信息
     */
    public static String getDriverClassByUrl(final String url) {
        Assert.notEmpty(url, "url 是必须参数");

        for(Map.Entry<String, String> entry : DRIVER_CLASS_MAP.entrySet()) {
            String urlPrefix = entry.getKey();
            if(url.startsWith(urlPrefix)) {
                DRIVER_CLASS_NAME = urlPrefix.substring(urlPrefix.indexOf(":")+1);
                return entry.getValue();
            }
        }

        logger.error("无法自动找到网址的匹配驱动程序类: " + url);
        return null;
    }

    /**
     * 关闭数据库连接
     * @param connection 连接对象
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                CONNECTION = null;
                System.gc();
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接出错: ", e);
        }
    }

}
