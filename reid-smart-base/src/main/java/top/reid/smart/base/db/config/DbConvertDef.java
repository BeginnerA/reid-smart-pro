package top.reid.smart.base.db.config;

import top.reid.smart.base.core.util.StrTools;

/**
 * <p>
 * 数据库转换定义
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 **/
public interface DbConvertDef {
    String YES = "Y";
    String NO = "N";
    /**
     * 数据库
     */
    String MYSQL = "mysql";
    String ORACLE = "oracle";
    String SQLSERVER = "sqlserver";
    String POSTGRESQL = "postgresql";
    String DM = "dm";
    String HSQLDB = "hsqldb";
    String H2 = "h2";
    String PHOENIX = "phoenix";
    String MARIADB = "mariadb";
    String SQLITE = "sqlite";
    String HERDDB = "herddb";
    String ORACLE9I = "oracle9i";
    String DB2 = "db2";
    String INFORMIX = "informix";
    String INFORMIX_SQLI = "informix-sqli";
    String SQLSERVER2012 = "sqlserver2012";
    String DERBY = "derby";
    String EDB = "edb";
    String OSCAR = "oscar";
    String KINGBASE = "kingbase";
    String CLICKHOUSE = "clickhouse";
    String HIGHGO = "highgo";
    String XUGU = "xugu";
    String ZENITH = "zenith";
    String POLARDB = "polardb";

    /**
     * 数据库表获取 SQL
     */
    String TABLE_SQL_1 = "select distinct table_name from information_schema.columns where table_schema = {0}";
    String TABLE_SQL_2 = "select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
    String TABLE_SQL_3 = "select distinct c.name as  table_name from sys.objects c where c.type = 'U'";
    String TABLE_SQL_4 = "select tablename from pg_tables where schemaname in( {0} )";
    /**
     * 数据库表结构获取 SQL
     */
    String TABLE_STRUCTURE_SQL_1 = "select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ordinal_position";
    String TABLE_STRUCTURE_SQL_2 = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}";
    String TABLE_STRUCTURE_SQL_3 = "select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as NVARCHAR(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id";
    String TABLE_STRUCTURE_SQL_4 = "select icm.column_name as field,icm.udt_name as type,fieldtxt.descript as comment, icm.numeric_precision_radix as column_precision ,icm.numeric_scale as column_scale ,icm.character_maximum_length as Char_Length,icm.is_nullable as attnotnull  from information_schema.columns icm, (SELECT A.attnum,( SELECT description FROM pg_catalog.pg_description WHERE objoid = A.attrelid AND objsubid = A.attnum ) AS descript,A.attname FROM pg_catalog.pg_attribute A WHERE A.attrelid = ( SELECT oid FROM pg_class WHERE relname = {0} ) AND A.attnum > 0 AND NOT A.attisdropped  ORDER BY A.attnum ) fieldtxt where icm.table_name={1} and fieldtxt.attname = icm.column_name";

    /**
     * 获取查询 SQL
     * @param mark 标记 0：数据库表获取 SQL，1：数据库表结构获取 SQL
     * @param diverName 连接驱动字符串
     * @return SQL
     */
    static String getSql(int mark, String diverName) {
        String sql = null;
        if (StrTools.contains(diverName, MYSQL) || StrTools.contains(diverName, MARIADB) || StrTools.contains(diverName, SQLITE) || StrTools.contains(diverName, CLICKHOUSE) || StrTools.contains(diverName, POLARDB)) {
            if (mark == 0) {
                sql = TABLE_SQL_1;
            }else if (mark == 1) {
                sql = TABLE_STRUCTURE_SQL_1;
            }
        }
        if (StrTools.contains(diverName, ORACLE9I) || StrTools.contains(diverName, ORACLE) || StrTools.contains(diverName, DM) || StrTools.contains(diverName, EDB)) {
            if (mark == 0) {
                sql = TABLE_SQL_2;
            }else if (mark == 1) {
                sql = TABLE_STRUCTURE_SQL_2;
            }
        }
        if (StrTools.contains(diverName, SQLSERVER) || StrTools.contains(diverName, SQLSERVER2012) || StrTools.contains(diverName, DERBY)) {
            if (mark == 0) {
                sql = TABLE_SQL_3;
            }else if (mark == 1) {
                sql = TABLE_STRUCTURE_SQL_3;
            }
        }
        if (StrTools.contains(diverName, POSTGRESQL) || StrTools.contains(diverName, KINGBASE) || StrTools.contains(diverName, ZENITH)) {
            if (mark == 0) {
                sql = TABLE_SQL_4;
            }else if (mark == 1) {
                sql = TABLE_STRUCTURE_SQL_4;
            }
        }
        return sql;
    }

    /**
     * 判断数据库类型是否包含在内<br>
     * 数据库：<br>
     *     1、{@link #MYSQL}<br>
     *     2、{@link #MARIADB}<br>
     *     3、{@link #SQLITE}<br>
     *     4、{@link #CLICKHOUSE}<br>
     *     5、{@link #POLARDB}<br>
     * @param diverName 连接驱动字符串
     * @return 是否包含在内
     */
    static boolean isThisType1(String diverName) {
        return StrTools.contains(diverName, MYSQL) || StrTools.contains(diverName, MARIADB) || StrTools.contains(diverName, SQLITE) || StrTools.contains(diverName, CLICKHOUSE) || StrTools.contains(diverName, POLARDB);
    }

    /**
     * 判断数据库类型是否包含在内<br>
     * 数据库：<br>
     *     1、{@link #ORACLE9I}<br>
     *     2、{@link #ORACLE}<br>
     *     3、{@link #DM}<br>
     *     4、{@link #EDB}<br>
     * @param diverName 连接驱动字符串
     * @return 是否包含在内
     */
    static boolean isThisType2(String diverName) {
        return StrTools.contains(diverName, ORACLE9I) || StrTools.contains(diverName, ORACLE) || StrTools.contains(diverName, DM) || StrTools.contains(diverName, EDB);
    }

    /**
     * 判断数据库类型是否包含在内<br>
     * 数据库：<br>
     *      1、{@link #SQLSERVER}<br>
     *      2、{@link #SQLSERVER2012}<br>
     *      3、{@link #DERBY}<br>
     * @param diverName 连接驱动字符串
     * @return 是否包含在内
     */
    static boolean isThisType3(String diverName) {
        return StrTools.contains(diverName, SQLSERVER) || StrTools.contains(diverName, SQLSERVER2012) || StrTools.contains(diverName, DERBY);
    }

    /**
     * 判断数据库类型是否包含在内<br>
     * 数据库：<br>
     *      1、{@link #POSTGRESQL}<br>
     *      2、{@link #KINGBASE}<br>
     *      3、{@link #ZENITH}<br>
     * @param diverName 连接驱动字符串
     * @return 是否包含在内
     */
    static boolean isThisType4(String diverName) {
        return StrTools.contains(diverName, POSTGRESQL) || StrTools.contains(diverName, KINGBASE) || StrTools.contains(diverName, ZENITH);
    }
}
