package top.reid.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 登录用户
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/23
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser {

    /**
     * 登录人 id
     */
    private String id;

    /**
     * 登录人账号
     */
    private String username;

    /**
     * 登录人名字
     */
    private String realName;

    /**
     * 登录人密码
     */
    private String password;

    /**
     * 当前登录部门 code
     */
    private String orgCode;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男，2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态(1：正常，2：冻结 ）
     */
    private Integer status;

    /**
     * 删除状态(0：正常，1：已删除)
     */
    private Integer delFlag;

    /**
     * 同步工作流引擎1同步0不同步
     */
    private Integer activitiSync;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *  身份（1 普通员工 2 上级）
     */
    private Integer userIdentity;

    /**
     * 管理部门 ids
     */
    private String departIds;

    /**
     * 职务，关联职务表
     */
    private String post;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 多租户 id 配置，编辑用户的时候设置
     */
    private String relTenantIds;

    /**
     * 设备 id 推送用
     */
    private String clientId;
}
