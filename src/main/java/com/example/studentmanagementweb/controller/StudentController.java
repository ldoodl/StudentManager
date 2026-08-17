package com.example.studentmanagementweb.controller;

import com.example.studentmanagementweb.common.Result;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.service.StudentService;
import com.example.studentmanagementweb.vo.StudentVO;

import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result<List<StudentVO>> getAll() {
        return Result.success(studentService.findAllStudentVOs());
    }

    @GetMapping("/{id}")
    public StudentVO findById(@PathVariable String id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("/search")
    public Result<List<StudentVO>> findByName(@RequestParam(required = false) String keyword) {
        return Result.success(studentService.findByName(keyword));
    }

    @GetMapping("/sorted")
    public Result<List<StudentVO>> getSortedByScore() {
        return Result.success(studentService.findAllStudentOrderByDesc());
    }

    @PostMapping
    public Result<String> add(@Valid @RequestBody Student student) {
        boolean success = studentService.addStudent(student);
        if (success) {
            return Result.success("添加成功");
        } else {
            return Result.error(400, "学号已存在或者添加失败");
        }
    }

    @PutMapping
    public Result<String> update(@Valid @RequestBody Student student) {
        boolean success = studentService.updateStudent(student);

        if (success) {
            return Result.success("修改成功");
        } else {
            return Result.error(400, "学号不存在或者修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        boolean success = studentService.deleteStudent(id);

        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error(400, "删除失败，学号不存在");
        }
    }

    @GetMapping("/page")
    public Result<PageInfo<StudentVO>> getPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        return Result.success(studentService.findStudentsByPage(pageNum, pageSize));
    }
}
