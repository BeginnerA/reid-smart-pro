package top.reid.smart.base.db.database.pojo;

/**
 * <p>
 * 表信息
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class TableVo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 引擎
     */
    private String engine;
    /**
     * 起始自增
     */
    private int initialIncrement;
    /**
     * 字符集
     */
    private String characterSet;
    /**
     * 排序规则
     */
    private String sortingRules;
    /**
     * 注释
     */
    private String annotation;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getInitialIncrement() {
        return initialIncrement;
    }

    public void setInitialIncrement(int initialIncrement) {
        this.initialIncrement = initialIncrement;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public String getSortingRules() {
        return sortingRules;
    }

    public void setSortingRules(String sortingRules) {
        this.sortingRules = sortingRules;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "TableVo{" +
                "tableName='" + tableName + '\'' +
                ", engine='" + engine + '\'' +
                ", initialIncrement=" + initialIncrement +
                ", characterSet='" + characterSet + '\'' +
                ", sortingRules='" + sortingRules + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
