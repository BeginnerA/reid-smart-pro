package org.reid.smart.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reid.smart.modules.system.entity.SysUser;
import org.reid.smart.modules.system.service.ISysUserService;
import org.reid.smart.modules.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import top.reid.system.vo.LoginUser;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public List<SysUser> querySysUserAll() {
        return this.list(new LambdaQueryWrapper<SysUser>());
    }

    @Override
    public Set<String> queryUserRoles(String username) {
        return null;
    }

    @Override
    public Set<String> queryUserAuths(String username) {
        return null;
    }

    @Override
    public LoginUser getUserByName(String username) {
        LoginUser user = new LoginUser();
        user.setUsername(username);
        user.setPassword("123456");
        user.setStatus(1);
        return user;
    }
}




