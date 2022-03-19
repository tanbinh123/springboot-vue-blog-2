package com.trent.vueblog.service.impl;

import com.trent.vueblog.entity.User;
import com.trent.vueblog.mapper.UserMapper;
import com.trent.vueblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
