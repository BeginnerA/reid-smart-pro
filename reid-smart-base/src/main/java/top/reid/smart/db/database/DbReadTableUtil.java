package top.reid.smart.db.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.smart.core.util.StrTools;
import top.reid.smart.db.config.DataSourceConfig;
import top.reid.smart.db.config.DbConvertDef;
import top.reid.smart.db.config.DriverClass;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据库读取操作工具
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class DbReadTableUtil {
    private static final Logger logger = LoggerFactory.getLogger(DbReadTableUtil.class);
    private static Connection connection;
    private static Statement statement;

    /**
     * 初始化
     */
    private static void init() {
        try {
            if (connection == null) {
                connection = DriverClass.getConnection(DataSourceConfig.diverName, DataSourceConfig.url, DataSourceConfig.username, DataSourceConfig.password);
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
        }catch (SQLException e) {
            logger.error("初始化数据库连接信息失败：", e);
        }
    }

    /**
     * 执行 SQL
     * @param sql SQL
     * @return ResultSet
     */
    public static ResultSet executeSql(String sql) {
        ResultSet rs = null;
        try {
            if (statement == null) {
                init();
            }
            logger.debug("--------------sql-------------" + sql);
            rs = statement.executeQuery(sql);
            rs.last();
        }catch (SQLException e) {
            logger.error("执行sql失败：", e);
        }
        return rs;
    }

    /**
     * 获取所有表名
     * @return 所有表名
     */
    public static List<String> readAllTableNames() {
        String sql = null;
        ArrayList<String> tables = new ArrayList<String>();

        try {
            init();
            String databaseName = connection.getCatalog();
            logger.info("连接数据库名称: " + databaseName);
            if (DbConvertDef.isThisType1(DataSourceConfig.diverName)) {
                sql = MessageFormat.format(DbConvertDef.TABLE_SQL_1, StrTools.splicingApostrophe(databaseName));
            }
            if (DbConvertDef.isThisType2(DataSourceConfig.diverName)) {
                sql = DbConvertDef.TABLE_SQL_2;
            }
            if (DbConvertDef.isThisType3(DataSourceConfig.diverName)) {
                sql = DbConvertDef.TABLE_SQL_3;
            }
            if (DbConvertDef.isThisType4(DataSourceConfig.diverName)) {
                String dataSource = DataSourceConfig.dataSource;
                if (!dataSource.contains(String.valueOf(CommonCharacter.COMMA))) {
                    sql = MessageFormat.format(DbConvertDef.TABLE_SQL_4, StrTools.splicingApostrophe(dataSource));
                }else {
                    StringBuilder param = new StringBuilder();
                    String[] params = dataSource.split(String.valueOf(CommonCharacter.COMMA));
                    int paramsLength = params.length;
                    for (String s : params) {
                        param.append(StrTools.splicingApostrophe(s)).append(CommonCharacter.COMMA);
                    }
                    sql = MessageFormat.format(DbConvertDef.TABLE_SQL_4, param.toString().substring(0, param.toString().length() - 1));
                }
            }

            ResultSet resultSet = executeSql(sql);
            while(resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.error("查询数据库表信息失败: " + e);
        } finally {
            closeStatement(statement);
        }

        return tables;
    }

    /**
     * 关闭Statement
     * @param stm SQL执行对象
     */
    private static void closeStatement(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
                statement = null;
                System.gc();
            }
        } catch (SQLException e) {
            logger.error("关闭 SQL 执行对象出错: ", e);
        }
    }

}
