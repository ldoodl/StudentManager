package com.example.studentmanagementweb.service;

import com.example.studentmanagementweb.dao.StudentMapper;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.vo.StudentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {


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
        Student s = studentMapper.findById(id);
        return new StudentVO (s.getId(), s.getName(), s.getAge(), s.getScore());
    }

    public List<StudentVO> findByName(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findAllStudentVOs();
        }
        return studentMapper.findByName(keyword.trim())
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

    @Transactional
    public boolean addStudent(Student student) {
        if (studentMapper.findById(student.getId()) != null) {
            return false;
        }
        return studentMapper.add(student) > 0;
    }

    @Transactional
    public boolean updateStudent(Student student) {
        return studentMapper.update(student) > 0;
    }

    @Transactional
    public boolean deleteStudent(String id) {
        return studentMapper.deleteById(id) > 0;
    }


}
