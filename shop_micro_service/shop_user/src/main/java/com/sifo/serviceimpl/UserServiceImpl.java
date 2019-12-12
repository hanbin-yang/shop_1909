package com.sifo.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sifo.dao.UserMapper;
import com.sifo.entity.User;
import com.sifo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public int register(User user) {

        //检查用户名是否存在
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        Integer count = userMapper.selectCount(wrapper);

        //检查邮箱是否被注册过
        QueryWrapper<User> wrapper1=new QueryWrapper<>();
        wrapper1.eq("email",user.getEmail());
        Integer count1 = userMapper.selectCount(wrapper1);

        if(count == 0)//用户名不存在则表示可以注册
        {
            if(count1==0){//用户名存在情况下，判断邮箱是否被注册过
                return userMapper.insert(user);
            }else {//邮箱存在，不可注册
                return -2;
            }

        }else {//用户名存在，不可注册

            return -1;
        }
    }

    @Override
    public User queryByUserName(String username) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }
}
