package com.example.studentmanagementweb.controller;

import com.example.studentmanagementweb.common.Result;
import com.example.studentmanagementweb.model.User;
import com.example.studentmanagementweb.service.UserService;
import com.example.studentmanagementweb.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        User user = userService.authenticate(username, password);

        if (user == null) {
            return Result.error(401, "用户名或者密码错误");
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
        String role = registerRequest.get("role");

        boolean success = userService.register(username, password, role);

        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error(400, "用户名已经存在");
        }
    }

}
