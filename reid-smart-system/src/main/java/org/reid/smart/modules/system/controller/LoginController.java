package org.reid.smart.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reid.smart.common.api.vo.Result;
import top.reid.smart.config.shiro.util.JwtTools;
import top.reid.smart.base.core.util.CommonCharacter;
import top.reid.smart.base.db.util.RedisTools;
import top.reid.smart.system.vo.SysLoginModel;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/6/15
 * @Version V1.0
 **/
@Api(tags="用户登录")
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Resource
    RedisTools redisTools;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody SysLoginModel sysLoginModel){
        // shiro
        // UsernamePasswordToken token = new UsernamePasswordToken(sysLoginModel.getUsername(), sysLoginModel.getPassword());
        // Subject subject = SecurityUtils.getSubject();
        // subject.login(token);
        // Object user = subject.getPrincipal();

        // shiro+jwt
        // 生成token
        String token = JwtTools.sign(sysLoginModel.getUsername(), sysLoginModel.getPassword());
        // 设置token缓存有效时间
        redisTools.set(CommonCharacter.PREFIX_USER_TOKEN + token, token);
        redisTools.expire(CommonCharacter.PREFIX_USER_TOKEN + token, JwtTools.EXPIRE_TIME * 2 / 1000);
        return new Result<>(200,"登录成功：" + token);
    }
}
