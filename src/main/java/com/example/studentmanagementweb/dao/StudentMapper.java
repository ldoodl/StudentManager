package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select ("SELECT * FROM student WHERE id = #{id}  AND created_by = #{username}")
    Student findById(@Param("id") String id, @Param("username") String username);

    @Select("SELECT * FROM student WHERE name LIKE CONCAT ('%', #{keyword}, '%') AND created_by = #{username}")
    List<Student> findByName(@Param("keyword") String keyword, @Param("username") String username);
    @Select("SELECT * FROM student ORDER BY score DESC")
    List<Student> findAllOrderByScoreDesc();

    @Insert ("INSERT INTO student (id, name, age, score, created_by) VALUES(#{id}, #{name}, #{age}, #{score}, #{createdBy})")
    int add(Student student);

    @Update("UPDATE student SET name = #{name}, age = #{age}, score = #{score} WHERE id = #{id} AND create_by#{username}")
    int update(Student student);

    @Delete ("DELETE FROM student WHERE id = #{id} AND created_by = #{username}")
    int deleteById(@Param("id") String id, @Param("username") String username);


}
