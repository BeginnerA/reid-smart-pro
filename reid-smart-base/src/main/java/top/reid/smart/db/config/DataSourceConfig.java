package top.reid.smart.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * <p>
 * 数据库连接配置
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    private static final String DATABASE_CONFIG_PATH = "reid/reid_database";
    private static final String BUILDER_CONFIG_PATH = "reid/reid_config";

    private static ResourceBundle databaseBundle = getBundle(DATABASE_CONFIG_PATH);
    private static ResourceBundle builderBundle = getBundle(BUILDER_CONFIG_PATH);

    public static String dataSource = "mysql";
    public static String diverName = "com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/reid-smart?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    public static String username = "root";
    public static String password = "root";
    public static String databaseName = "reid-smart";

    public DataSourceConfig() {

    }

    static {
        if (databaseBundle == null) {
            logger.debug("通过 class 目录加载配置文件: " + DATABASE_CONFIG_PATH);
            databaseBundle = ResourceBundle.getBundle(DATABASE_CONFIG_PATH);
        }

        if (builderBundle == null) {
            logger.debug("通过 class 目录加载配置文件: " + BUILDER_CONFIG_PATH);
            builderBundle = ResourceBundle.getBundle(BUILDER_CONFIG_PATH);
        }

        url = databaseBundle.getString("url");
        diverName = DriverClass.getDriverClassByUrl(url);
        dataSource = DriverClass.DRIVER_CLASS_NAME;
        username = databaseBundle.getString("username");
        password = databaseBundle.getString("password");
        databaseName = databaseBundle.getString("database_name");
    }

    private static ResourceBundle getBundle(String path) {
        PropertyResourceBundle resourceBundle = null;
        BufferedInputStream inputStream = null;
        String configuration = System.getProperty("user.dir") + File.separator + "resources" + File.separator + path + ".properties";

        try {
            inputStream = new BufferedInputStream(new FileInputStream(configuration));
            resourceBundle = new PropertyResourceBundle(inputStream);
            inputStream.close();
            logger.debug(" JAR 方式部署，通过 config 目录读取配置：" + configuration);
        } catch (IOException e) {
            logger.debug("读取：" + path + " 失败");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return resourceBundle;
    }

    public static String getUrl() {
        return databaseBundle.getString("url");
    }

    public static String getUsername() {
        return databaseBundle.getString("username");
    }

    public static String getPassword() {
        return databaseBundle.getString("password");
    }

}

