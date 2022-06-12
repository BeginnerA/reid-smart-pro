package top.reid.smart.db.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 查询条件
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/7
 * @Version V1.0
 **/
@Data
public class QueryCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 4740166316629191651L;

    private String field;
    /** 组件的类型（例如：input、select、radio） */
    private String type;
    /**
     * 对应的数据库字段的类型
     * 支持：int、bigDecimal、short、long、float、double、boolean
     */
    private String dbType;
    private String rule;
    private String val;
}
