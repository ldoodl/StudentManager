package com.example.studentmanagementweb.service;

import com.example.studentmanagementweb.dao.UserMapper;
import com.example.studentmanagementweb.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean register(String username, String password, String role) {
        if (userMapper.findByName(username) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : "USER");
        userMapper.insert(user);
        return true;
    }

    public User authenticate(String username, String password) {
        User user = userMapper.findByName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}
