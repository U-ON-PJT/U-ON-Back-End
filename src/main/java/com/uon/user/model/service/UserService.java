package com.uon.user.model.service;

import com.uon.user.dto.User;
public interface UserService {
    int idExist(String userId);
    int register(User user);
    String login(User user);
}
