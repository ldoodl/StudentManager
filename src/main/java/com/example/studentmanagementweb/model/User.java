package com.example.studentmanagementweb.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Date createAt;

    public User(){}
    public User(Long id, String username, String password, String role, Date createAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createAt = createAt;
    }
}
