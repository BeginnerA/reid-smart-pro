package org.reid.smart.modules.system.service;

import org.reid.smart.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> querySysUserAll();

}
