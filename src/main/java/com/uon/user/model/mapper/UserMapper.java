package com.uon.user.model.mapper;

import com.uon.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int idExist(String userId);
    String findByName(Map<String, String> param);
    int register(User user);
    User login(String userId);
    int deleteUser(String userId);
    User findByDongCode(String dongCode);
    User findById(String userId);
    String getPassword(String userId);
    int updateUser(User user);
    int updatePassword(User user);
    List<String> getId(User user);
}
