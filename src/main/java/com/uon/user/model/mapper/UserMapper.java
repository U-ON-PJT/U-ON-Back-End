package com.uon.user.model.mapper;

import com.uon.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User login(String userId);
    int idExist(String userId);
    int register(User user);

    int getLevel(int experience);
}
