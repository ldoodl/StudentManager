package com.example.studentmanagementweb.controller;

import com.example.studentmanagementweb.common.Result;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result<List<Student>> getAll() {
        return Result.success(studentService.findAllStudents());
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable String id) {
        return studentService.findStudentById(id);
    }

    @PostMapping
    public Result<String> add(@Valid @RequestBody Student student) {
        boolean success = studentService.addStudent(student);
        if (success) {
            return Result.success("添加成功");
        } else {
            return Result.error("学号已存在或者添加失败");
        }
    }

    @PutMapping
    public Result<String> update(@Valid @RequestBody Student student) {
        boolean success = studentService.updateStudent(student);

        if (success) {
            return Result.success("修改成功");
        } else {
            return Result.error("学号不存在或者修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        boolean success = studentService.deleteStudent(id);

        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败，学号不存在");
        }
    }
}
