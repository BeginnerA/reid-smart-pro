package top.reid.smart.db.database.pojo;

/**
 * <p>
 * 字段信息
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class ColumnVo {
    /**
     * 序号
     */
    private int serialNumber;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 最大长度
     */
    private int charMaxLength;
    /**
     * 精度
     */
    private int precision;
    /**
     * 非空
     */
    private boolean nullable;
    /**
     * 自增
     */
    private boolean selfIncreasing;
    /**
     * 主键策略
     */
    private String primaryKeyPolicy;
    /**
     * 默认值
     */
    private String defaults;
    /**
     * 额外的
     */
    private String additional;
    /**
     * Expression
     */
    private String expression;
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

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getCharMaxLength() {
        return charMaxLength;
    }

    public void setCharMaxLength(int charMaxLength) {
        this.charMaxLength = charMaxLength;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isSelfIncreasing() {
        return selfIncreasing;
    }

    public void setSelfIncreasing(boolean selfIncreasing) {
        this.selfIncreasing = selfIncreasing;
    }

    public String getPrimaryKeyPolicy() {
        return primaryKeyPolicy;
    }

    public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
        this.primaryKeyPolicy = primaryKeyPolicy;
    }

    public String getDefaults() {
        return defaults;
    }

    public void setDefaults(String defaults) {
        this.defaults = defaults;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
        return "ColumnVo{" +
                "serialNumber=" + serialNumber +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", charMaxLength=" + charMaxLength +
                ", precision=" + precision +
                ", nullable=" + nullable +
                ", selfIncreasing=" + selfIncreasing +
                ", primaryKeyPolicy='" + primaryKeyPolicy + '\'' +
                ", defaults='" + defaults + '\'' +
                ", additional='" + additional + '\'' +
                ", expression='" + expression + '\'' +
                ", characterSet='" + characterSet + '\'' +
                ", sortingRules='" + sortingRules + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
