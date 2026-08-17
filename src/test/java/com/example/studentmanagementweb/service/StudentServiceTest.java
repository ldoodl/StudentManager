package com.example.studentmanagementweb.service;

import com.example.studentmanagementweb.dao.StudentMapper;
import com.example.studentmanagementweb.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testAddAndFind() {
        Student s = new Student("T001", "测试", 22, 66.5);
        studentService.addStudent(s);
        assertNotNull(studentService.findStudentById("T001"));
    }
}
