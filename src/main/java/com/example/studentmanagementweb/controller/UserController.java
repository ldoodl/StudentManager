package com.example.studentmanagementweb.controller;

import com.example.studentmanagementweb.model.User;
import com.example.studentmanagementweb.common.Result;
import com.example.studentmanagementweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Result<User> getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        user.setPassword(null);
        return Result.success(user);
    }

    @PostMapping("/change-password")
    public Result<String> changePassword(@RequestBody Map<String, String> request) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        if (oldPassword == null || newPassword == null || newPassword.length() < 6) {
            return Result.error(400, "新密码长度至少为6位");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean success = userService.changePassword(username, oldPassword, newPassword);

        if (success) {
            return Result.success("密码修改成功");
        } else {
            return Result.error(400, "原密码错误");
        }
    }

}
