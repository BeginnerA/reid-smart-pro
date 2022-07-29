package top.reid.smart.base.db.query;

import top.reid.smart.base.core.util.StrTools;

/**
 * <p>
 * 查询链接规则
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
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
