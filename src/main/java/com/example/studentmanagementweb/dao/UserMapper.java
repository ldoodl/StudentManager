package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByName(@Param("username") String username);

    @Insert("INSERT INTO user (username, password, role) VALUES (#{username}, #{password}, #{role})")
    int insert(User user);
}
