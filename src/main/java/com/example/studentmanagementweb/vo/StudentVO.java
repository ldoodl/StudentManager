package com.example.studentmanagementweb.vo;
import com.example.studentmanagementweb.model.Student;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    @Schema(description = "学号")
    private String id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "年龄")
    private int age;

    @Schema(description = "成绩")
    private double score;


//    public StudentVO(String id, String name, int age, double score) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.score = score;
//    }

    public StudentVO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.score = student.getScore();
    }

}
