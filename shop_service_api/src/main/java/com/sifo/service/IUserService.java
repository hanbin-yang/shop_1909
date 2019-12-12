package com.sifo.service;

import com.sifo.entity.User;

public interface IUserService {
    int register(User user);

    User queryByUserName(String username);
}
