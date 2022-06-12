package org.reid.smart.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.reid.smart.modules.system.service.ISysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reid.common.api.vo.Result;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @Author REID
 * @Data 2022/3/8
 * @Version V1.0
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    ISysUserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/list")
    public Result<?> querySysUserAll() {
        return Result.ok(userService.querySysUserAll());
    }
}
