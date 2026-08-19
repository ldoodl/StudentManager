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

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {


    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findAllStudents() {
        return studentMapper.findAll();
    }

    public List<StudentVO> findAllStudentVOs() {
        List<Student> list = studentMapper.findAll();
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
        List<Student> students = studentMapper.findAll();
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
    public boolean deleteStudent(String id) {
        String currentUser = getCurrentUsername();

        log.info("开始删除学生信息，学号：{}", id);
        return studentMapper.deleteById(id, currentUser) > 0;
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Transactional
    public void addStudent(Student student) {
        String currentUser = getCurrentUsername();

        student.setCreatedBy(currentUser);
        if (studentMapper.findById(student.getId(), currentUser) != null) {
            throw new BusinessException(400, "学号已存在，请勿重复添加");

        }
        studentMapper.add(student);
    }

}
