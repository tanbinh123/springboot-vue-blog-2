package com.trent.vueblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trent.vueblog.dto.Result;
import com.trent.vueblog.entity.Blog;
import com.trent.vueblog.service.BlogService;
import com.trent.vueblog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Trent Lee
 * @since 2022-02-22
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public Result list(@RequestParam(defaultValue = "1") Integer pageNumber,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Blog> page = new Page<>(pageNumber, pageSize);
        IPage<Blog> blogs = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(blogs);
    }

    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "不存在该博客");
        return Result.success(blog);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/blog/edit", method = RequestMethod.POST)
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        // 修改或新增
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            // 证明是自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }
}
