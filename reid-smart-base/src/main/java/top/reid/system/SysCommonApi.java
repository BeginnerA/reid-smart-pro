package top.reid.system;

import top.reid.system.vo.LoginUser;

import java.util.Set;

/**
 * <p>
 * 涉及系统的公共 API 接口<br>
 * 接口具体实现需要在业务系统中进行实现
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/23
 * @Version V1.0
 **/
public interface SysCommonApi {

    /**
     * 查询用户角色信息
     * @param username 用户名
     * @return 用户角色信息集合
     */
    Set<String> queryUserRoles(String username);

    /**
     * 查询用户权限信息
     * @param username 用户名
     * @return 用户权限信息集合
     */
    Set<String> queryUserAuths(String username);

    /**
     * 根据用户账号查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    LoginUser getUserByName(String username);
}
