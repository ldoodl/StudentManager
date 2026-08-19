package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO user (username, password, role) VALUES (#{username}, #{password}, #{role})")
    int insert(User user);

    @Update("UPDATE user SET password = #{password} WHERE username = #{username}")
    int updatePassword(User user);
}
