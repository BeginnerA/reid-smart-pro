package top.reid.smart.db.query;

import top.reid.smart.core.util.StrTools;

/**
 * <p>
 * 查询链接规则
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/2/7
 * @Version V1.0
 **/
public enum MatchTypeEnum {
    /**
     * AND
     */
    AND("AND"),
    /**
     * OR
     */
    OR("OR");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 通过 value 值获取查询链接规则
     * @param value 值
     * @return 查询链接规则
     */
    public static MatchTypeEnum getByValue(Object value) {
        if (StrTools.isEmpty((CharSequence) value)) {
            return null;
        }
        return getByValue(value.toString());
    }

    /**
     * 通过 value 值获取查询链接规则
     * @param value 值
     * @return 查询链接规则
     */
    public static MatchTypeEnum getByValue(String value) {
        if (StrTools.isEmpty(value)) {
            return null;
        }
        for (MatchTypeEnum val : values()) {
            if (val.getValue().toLowerCase().equals(value.toLowerCase())) {
                return val;
            }
        }
        return null;
    }
}
