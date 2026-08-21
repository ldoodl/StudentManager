package com.example.studentmanagementweb.controller;

import com.alibaba.excel.EasyExcel;
import com.example.studentmanagementweb.common.Result;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.service.StudentService;
import com.example.studentmanagementweb.util.StudentImportListener;
import com.example.studentmanagementweb.vo.StudentVO;

import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ApplicationContext applicationContext;

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

//        boolean success = studentService.addStudent(student);
//        if (success) {
//            return Result.success("添加成功");
//        } else {
//            return Result.error(400, "学号已存在或者添加失败");
//        }

        studentService.addStudent(student);
        return Result.success("添加成功");
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
       studentService.deleteStudent(id);
            return Result.success("删除成功");
    }

    @GetMapping("/page")
    public Result<PageInfo<StudentVO>> getPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize)
    {
        return Result.success(studentService.findStudentsByPage(pageNum, pageSize));
    }
    @PostMapping("/import")
    public Result<String> importStudents(@RequestParam("file")MultipartFile file)
            throws IOException {
        if (file.isEmpty()) {
            return Result.error(400, "文件为空");
        }

        StudentImportListener listener = applicationContext.getBean(StudentImportListener.class);
        EasyExcel.read(file.getInputStream(), Student.class, listener).sheet().doRead();

        return Result.success("导入成功");
    }
    @GetMapping("/deleted")
    public Result<List<StudentVO>> getDeleted() {
        return Result.success(studentService.findDeletedStudents());
    }
    @GetMapping("/recycle")
    public Result<List<StudentVO>> getRecycleBin() {
        return Result.success(studentService.findDeletedStudents());
    }
    @PutMapping("/recycle/{id}/restore")
    public Result<String> restore(@PathVariable String id) {
        studentService.restoreStudents(id);
        return Result.success("恢复成功");
    }

    @Delete("/recycle/{id}/permanent")
    public Result<String> permanentDelete(@PathVariable String id) {
        studentService.deleteStudent(id);
        return Result.success("彻底删除成功");
    }

}
