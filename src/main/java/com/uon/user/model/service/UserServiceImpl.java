package com.uon.user.model.service;

import com.uon.user.dto.User;
import com.uon.user.model.mapper.UserMapper;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public int register(User user) {
        if(userMapper.idExist(user.getUserId()) == 1) return 0;

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userMapper.register(user);
    }

    @Override
    public int idExist(String userId) {
        return userMapper.idExist(userId);
    }

    @Override
    public String login(User user) {
        String id = user.getUserId();
        String password = user.getPassword();

        User userInfo = userMapper.login(id);
//		System.out.println(user);
//		System.out.println("비번1234암호화:"+passwordEncoder.encode(password));
        if(userInfo==null || !passwordEncoder.matches(password, userInfo.getPassword()) ) return null;

        int exp = userInfo.getExperience();

        if(exp >= 5000 ) userInfo.setLevel(5);
        else if(exp >= 3000) userInfo.setLevel(4);
        else if(exp >= 2000) userInfo.setLevel(3);
        else if (exp >= 1000) userInfo.setLevel(2);
        else userInfo.setLevel(1);

        System.out.println(userInfo);
        return jwtUtil.generateToken(userInfo);
    }

    @Override
    public int deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }


}
