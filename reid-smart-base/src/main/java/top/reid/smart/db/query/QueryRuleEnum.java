package top.reid.smart.db.query;

import top.reid.smart.core.util.StrTools;

/**
 * <p>
 * Query 规则常量
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/2/7
 * @Version V1.0
 **/
public enum QueryRuleEnum {
    /**
     * 大于
     */
    GT(">","gt","大于"),
    /**
     * 大于等于
     */
    GE(">=","ge","大于等于"),
    /**
     * 小于
     */
    LT("<","lt","小于"),
    /**
     * 小于等于
     */
    LE("<=","le","小于等于"),
    /**
     * 等于
     */
    EQ("=","eq","等于"),
    /**
     * 不等于
     */
    NE("!=","ne","不等于"),
    /**
     * 包含
     */
    IN("IN","in","包含"),
    /**
     * 全模糊
     */
    LIKE("LIKE","like","全模糊"),
    /**
     * 左模糊
     */
    LEFT_LIKE("LEFT_LIKE","left_like","左模糊"),
    /**
     * 右模糊
     */
    RIGHT_LIKE("RIGHT_LIKE","right_like","右模糊"),
    /**
     * 带加号等于
     */
    EQ_WITH_ADD("EQWITHADD","eq_with_add","带加号等于"),
    /**
     * 多词模糊匹配
     */
    LIKE_WITH_AND("LIKEWITHAND","like_with_and","多词模糊匹配"),
    /**
     * 自定义 SQL 片段
     */
    SQL_RULES("USE_SQL_RULES","ext","自定义SQL片段");

    private String value;

    private String condition;

    private String msg;

    QueryRuleEnum(String value, String condition, String msg){
        this.value = value;
        this.condition = condition;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 通过 value 值获取 Query 规则常量
     * @param value 值
     * @return Query 规则常量
     */
    public static QueryRuleEnum getByValue(String value){
        if(StrTools.isEmpty(value)) {
            return null;
        }
        for(QueryRuleEnum val : values()){
            if (val.getValue().equals(value) || val.getCondition().equals(value)){
                return val;
            }
        }
        return  null;
    }
}
