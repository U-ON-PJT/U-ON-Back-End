package com.uon.user.model.service;

import com.uon.user.dto.User;
public interface UserService {
    int register(User user);
    int idExist(String userId);
    String login(User user);
    int deleteUser(String userId);


}
