package com.example.studentmanagementweb.service;

import com.example.studentmanagementweb.dao.StudentMapper;
import com.example.studentmanagementweb.exception.BusinessException;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.vo.StudentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {


    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findAllStudents() {
        String currentUsername = getCurrentUsername();
        return studentMapper.findAll(currentUsername);
    }

    public List<StudentVO> findAllStudentVOs() {

        String currentUsername = getCurrentUsername();

        List<Student> list = studentMapper.findAll(currentUsername);
        return list.stream().map(s -> new StudentVO(s.getId(),  s.getName(), s.getAge(),s.getScore())).collect(Collectors.toList());

    }

    public StudentVO findStudentById(String id) {
        String currentUser = getCurrentUsername();
        Student s = studentMapper.findById(id, currentUser);
        return new StudentVO (s.getId(), s.getName(), s.getAge(), s.getScore());
    }

    public List<StudentVO> findByName(String keyword) {

        String currentUser = getCurrentUsername();

        if (keyword == null || keyword.isEmpty()) {
            return findAllStudentVOs();
        }
        return studentMapper.findByName(keyword.trim(), currentUser)
                .stream()
                .map(StudentVO :: new)
                .collect(Collectors.toList());
    }

    public List<StudentVO> findAllStudentOrderByDesc() {
        return studentMapper.findAllOrderByScoreDesc()
                .stream()
                .map(StudentVO :: new)
                .collect(Collectors.toList());
    }

    public PageInfo<StudentVO> findStudentsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String currentUsername = getCurrentUsername();
        List<Student> students = studentMapper.findAll(currentUsername);
        List<StudentVO> studentvo = students.stream().map(StudentVO :: new).collect(Collectors.toList());
        return new PageInfo<>(studentvo);

    }

//    @Transactional
//    public boolean addStudent(Student student) {
//
//        log.info("开始添加学生，学号：{}", student.getId());
//        if (studentMapper.findById(student.getId()) != null) {
//            return false;
//        }
//        log.info("添加成功");
//        return studentMapper.add(student) > 0;
//    }

    @Transactional
    public boolean updateStudent(Student student) {
        log.info("开始更新学生信息，学号：{}", student.getId());
        return studentMapper.update(student) > 0;

    }

    @Transactional
    public void deleteStudent(String id) {
        String currentUser = getCurrentUsername();

        log.info("开始删除学生信息，学号：{}", id);
        int row = studentMapper.softDeleteById(id, currentUser);
        if (row < 0) {
            throw new BusinessException(404, "未找到该学生或者无权限");
        }
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Transactional
    public void addStudent(Student student) {
        String currentUser = getCurrentUsername();
        student.setCreatedBy(currentUser);

        // ✅ 正确：检查全局学号是否已被占用
        if (studentMapper.findByIdGlobal(student.getId()) != null) {
            throw new BusinessException(400, "学号已存在，请勿重复添加");
        }

        studentMapper.add(student);
    }

    public Map<String, Object> getStatistics() {
        String username = getCurrentUsername();
        List<Student> list = studentMapper.findAll(username);
        Map<String, Object> result = new HashMap<>();

        if (list.isEmpty()) {
            result.put("total", 0);
            result.put("avg", 0);
            result.put("max", 0);
            result.put("min", 0);
            result.put("scoreSegments", new int[]{0, 0, 0, 0});
            return result;
        }

        double sum = 0;
        double max = 0;
        double min = 0;
        int[] segments = new int[5];// 0:0-59, 1:60-69, 2:70-79, 3:80-89, 4:90-100
        for (Student s : list) {
            double score = s.getScore();
            sum += score;
            if (score > max) max = score;
            if (score < min) min = score;
            if (score < 60) segments[0] ++;
            else if (score < 70) segments[1] ++;
            else if (score < 80) segments[2] ++;
            else if (score < 90) segments[3] ++;
            else segments[4] ++;

        }

        result.put("total", list.size());
        result.put("avg", sum / list.size());
        result.put("max", max);
        result.put("min", min);
        result.put("scoreSegments", segments);
        return result;
    }

    public List<StudentVO> findDeletedStudents() {
        String currentUsername = getCurrentUsername();
        return studentMapper.findDeleted(currentUsername)
                .stream().map(StudentVO :: new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void restoreStudents(String id) {
        String currentUsername = getCurrentUsername();

        int rows = studentMapper.restoreById(id, currentUsername);
        if (rows < 0) {
            throw new BusinessException(404, "恢复失败，未找到学号");
        }
    }

    @Transactional
    public void permanentStudent(String id) {
        String currentUsername = getCurrentUsername();
        int rows = studentMapper.permanentDeleteById(id, currentUsername);
        if (rows < 0) {
            throw new BusinessException(404, "删除失败，未找到学号");
        }
    }
}
