package top.reid.system.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统用户缓存信息
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/2/7
 * @Version V1.0
 **/
@Data
public class SysUserCacheInfo {
    /**
     * 系统用户代码
     */
    private String sysUserCode;
    /**
     * 系统用户名
     */
    private String sysUserName;
    /**
     * 系统组织代码
     */
    private String sysOrgCode;
    /**
     * 系统多组织代码
     */
    private List<String> sysMultiOrgCode;
    /**
     * 状态
     */
    private boolean oneDepart;
}
