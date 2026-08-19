package com.example.studentmanagementweb.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.studentmanagementweb.model.Student;
import com.example.studentmanagementweb.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;



@Slf4j
@Component
public class StudentImportListener implements ReadListener<Student> {
    @Autowired
    private StudentService studentService;

    private static final int BATCH_SIZE = 100;
    private List<Student> cacheList = new ArrayList<>();

    @Override
    public void invoke(Student student, AnalysisContext context) {
        cacheList.add(student);
        if (cacheList.size() >= BATCH_SIZE) {
            saveBatch();
            cacheList.clear();
        }
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (!cacheList.isEmpty()) {
            saveBatch();
            cacheList.clear();
        }
        log.info("所以数据导入成功");
    }

    private void saveBatch() {
        for (Student s : cacheList) {
            try {
                studentService.addStudent(s);
            } catch(Exception e) {
                log.warn("导入学生{} 失败： {}", s.getId(), e.getMessage());
            }
        }
    }
}
