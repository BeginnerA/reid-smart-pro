package top.reid.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 菜单权限规则
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/26
 * @Version V1.0
 **/
@Data
public class SysPermissionDataRuleModel {
    /**
     * id
     */
    private String id;

    /**
     * 对应的菜单id
     */
    private String permissionId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 字段
     */
    private String ruleColumn;

    /**
     * 条件
     */
    private String ruleConditions;

    /**
     * 规则值
     */
    private String ruleValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;
}
