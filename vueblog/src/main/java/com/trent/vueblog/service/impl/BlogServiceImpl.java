package com.trent.vueblog.service.impl;

import com.trent.vueblog.entity.Blog;
import com.trent.vueblog.mapper.BlogMapper;
import com.trent.vueblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Trent Lee
 * @since 2022-02-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
