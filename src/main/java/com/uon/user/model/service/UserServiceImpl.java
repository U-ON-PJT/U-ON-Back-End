package com.uon.user.model.service;

import com.uon.user.dto.User;
import com.uon.user.model.mapper.UserMapper;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public int idExist(String userId) {
        return userMapper.idExist(userId);
    }

    @Override
    @Transactional
    public int register(User user) {
        if(userMapper.idExist(user.getUserId()) == 1) return 0;

        Map<String, String> param = new HashMap<>();
        param.put("sidoName", user.getSidoName());
        param.put("gugunName", user.getGugunName());

        user.setDongCode(userMapper.findByName(param));

        if(user.getDongCode() == null) return 0;

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userMapper.register(user);
    }

    @Override
    public String login(User user) {
        String id = user.getUserId();
        String password = user.getPassword();

        User userInfo = userMapper.login(id);

        if(userInfo==null || !passwordEncoder.matches(password, userInfo.getPassword()) ) return null;

        int exp = userInfo.getExperience();

        if(exp >= 5000 ) userInfo.setLevel(5);
        else if(exp >= 3000) userInfo.setLevel(4);
        else if(exp >= 2000) userInfo.setLevel(3);
        else if (exp >= 1000) userInfo.setLevel(2);
        else userInfo.setLevel(1);

        return jwtUtil.generateToken(userInfo);
    }

    @Override
    public int deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    @Transactional
    public User findById(String userId) {
        // user table에서 password, level, experience, role 안가져 옴
        User user = userMapper.findById(userId);

        User location =  userMapper.findByDongCode(user.getDongCode());

        user.setSidoName(location.getSidoName());
        user.setGugunName(location.getGugunName());

        return user;
    }

    @Override
    @Transactional
    public int updateUser(User user) {

        Map<String, String> param = new HashMap<>();
        param.put("sidoName", user.getSidoName());
        param.put("gugunName", user.getGugunName());

        user.setDongCode(userMapper.findByName(param));

        if(user.getDongCode() == null) return 0;

        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public int updatePassword(User user) {
        String password = userMapper.getPassword(user.getUserId());

        if(password == null || !passwordEncoder.matches(user.getPassword(), password)) return 0;

        String encodedPassword = passwordEncoder.encode(user.getNewPassword());
        user.setNewPassword(encodedPassword);
        System.out.println(user.getNewPassword());

        return userMapper.updatePassword(user);
    }

    @Override
    public List<String> getId(User user) {
        return userMapper.getId(user);
    }
}
