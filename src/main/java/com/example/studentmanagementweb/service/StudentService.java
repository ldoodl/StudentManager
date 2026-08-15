package com.example.studentmanagementweb.service;

import com.example.studentmanagementweb.dao.StudentMapper;
import com.example.studentmanagementweb.model.Student;
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

    public Student findStudentById(String id) {
        return studentMapper.findById(id);
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

//    private StudentMapper dao = new StudentMapper();
//
//    public void loadData() {
//        dao.loadData();
//    }
//
//    public void saveData() {
//        dao.saveData();
//    }
//
//    public List<Student> findAllStudents() {
//        return dao.findAll();
//    }
//
//    public boolean addStudent(Student student) {
//        if (dao.findById(student.getId()) != null) {
//            return false;
//        }
//        dao.add(student);
//        dao.saveData();
//        return true;
//    }
//
//    public boolean deleteStudent(String id) {
//        boolean result = dao.deleteById(id);
//        if (result) {
//            dao.saveData();
//        }
//        return result;
//    }
//
//    public boolean updateStudent(String id, String newName, int newAge, double newScore) {
//        Student old = dao.findById(id);
//        if (old == null) {
//            return false;
//        }
//
//        if (newName != null && !newName.trim().isEmpty()) {
//            old.setName(newName);
//        }
//        if (newAge > 0) {
//            old.setAge(newAge);
//        }
//        if (newScore >= 0 && newScore <= 100) {
//            old.setScore(newScore);
//        }
//        old.setName(newName);
//        old.setAge(newAge);
//        old.setScore(newScore);
//
//        dao.update(old);
//        dao.saveData();
//        return true;
//    }
//
//    public List<Student> searchByName(String keyWord) {
//        if (keyWord == null || keyWord.trim().isEmpty()) {
//            return dao.findAll();
//        }
//        return dao.findAll().stream().filter(
//                s ->s.getName().toLowerCase().contains(keyWord.toLowerCase())
//        ).collect(Collectors.toList());
//    }
//
//    public List<Student> sortByScoreDesc() {
//        List<Student> list = dao.findAll();
//        list.sort((s1, s2) -> Double.compare(s2.getScore(), s1.getScore()));
//
//        return list;
//    }
//
//    public List<Student> sortByAge() {
//        List<Student> list = dao.findAll();
//        list.sort(Comparator.comparingInt(Student::getAge));
//        return list;
//    }
//
//    public String getStatistics() {
//        List<Student> list = dao.findAll();
//
//        if (list.isEmpty()) {
//            return "暂无学生数据，无法进行统计";
//        }
//
//        DoubleSummaryStatistics stats = list.stream()
//                .mapToDouble(Student::getScore)
//                .summaryStatistics();
//        long count = list.size();
//        double avg = stats.getAverage();
//        double max =stats.getMax();
//        double min = stats.getMin();
//
//        long passCount = list.stream().filter(s -> s.getScore() >= 60).count();
//        double passRate = (passCount * 100.0) / count;
//
//        return String.format(
//                "📊 班级统计报告\n" +
//                        "总人数：%d 人\n" +
//                        "平均分：%.2f 分\n" +
//                        "最高分：%.2f 分\n" +
//                        "最低分：%.2f 分\n" +
//                        "及格人数：%d 人\n" +
//                        "及格率：%.1f%%",
//                count, avg, max, min, passCount, passRate
//        );
//    }
}
