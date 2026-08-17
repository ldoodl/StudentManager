package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select ("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") String id);

    @Select("SELECT * FROM student WHERE name LIKE CONCAT ('%', #{keyword}, '%')")
    List<Student> findByName(@Param("keyword") String keyword);
    @Select("SELECT * FROM student ORDER BY score DESC")
    List<Student> findAllOrderByScoreDesc();

    @Insert ("INSERT INTO student (id, name, age, score) VALUES(#{id}, #{name}, #{age}, #{score})")
    int add(Student student);

    @Update("UPDATE student SET name = #{name}, age = #{age}, score = #{score} WHERE id = #{id}")
    int update(Student student);

    @Delete ("DELETE FROM student WHERE id = #{id}")
    int deleteById(@Param("id") String id);


}
