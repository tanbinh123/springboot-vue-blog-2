package com.trent.vueblog.controller;


import com.trent.vueblog.dto.Result;
import com.trent.vueblog.entity.User;
import com.trent.vueblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Trent Lee
 * @since 2022-02-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequiresAuthentication
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Result index() {
        User user = userService.getById(1L);
        return Result.success(user);

    }

    // @Validated 表示参数需要校验，具体校验字段在类中
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@Validated @RequestBody User user) {
        return Result.success(user);
    }

}
