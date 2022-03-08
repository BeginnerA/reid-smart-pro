package org.reid.smart.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reid.smart.modules.system.entity.SysUser;
import org.reid.smart.modules.system.service.ISysUserService;
import org.reid.smart.modules.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public List<SysUser> querySysUserAll() {
        return this.list(new LambdaQueryWrapper<SysUser>());
    }
}




