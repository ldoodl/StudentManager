package com.example.studentmanagementweb.controller;

import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAll() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable String id) {
        return studentService.findStudentById(id);
    }

    @PostMapping
    public boolean add(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public boolean update(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }
}
