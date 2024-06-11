package com.uon.user.model.mapper;

import com.uon.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int register(User user);
    int idExist(String userId);
    User login(String userId);
    int deleteUser(String userId);
}
