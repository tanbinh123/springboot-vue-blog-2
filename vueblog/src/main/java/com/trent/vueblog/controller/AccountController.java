package com.trent.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trent.vueblog.dto.LoginDTO;
import com.trent.vueblog.dto.Result;
import com.trent.vueblog.entity.User;
import com.trent.vueblog.service.UserService;
import com.trent.vueblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("username", loginDTO.getUsername()));
        Assert.notNull(user, "用户不存在");

        // 数据库中的密码使用 MD5 存储
        if (!user.getPassword().equals(SecureUtil.md5(loginDTO.getPassword()))) {
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
