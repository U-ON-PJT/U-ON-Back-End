package com.uon.user.model.service;

import com.uon.user.dto.User;

import java.util.List;

public interface UserService {
    int idExist(String userId);
    int register(User user);
    String login(User user);
    int deleteUser(String userId);
    User findById(String userId);
    User getUserInfo(String userId);
    int updateUser(User user);
    int updatePassword(User user);
    List<String> getId(User user);

}
