package com.example.studentmanagementweb.model;

import java.io.Serializable;
//import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "学号不能为空")
    private String id;

    @NotBlank(message = "姓名不能为空")
    @Size(min = 1, max = 20, message = "姓名长度必须在1-20之间")
    private String name;

    @Min(value = 1, message = "年龄最小为1")
    @Max(value = 150, message = "年龄最大为150")
    private int age;

    @Min(value = 0, message = "成绩最小为0")
    @Max(value = 100, message = "成绩最大为150")
    private double score;

//    public Student (String id, String name, int age, double score) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.score = score;
//    }

//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    public double getScore() { return score; }
//    public void setAge(int age) { this.age = age; }
//    public int getAge() { return age; }
//    public void setScore(double score) { this.score = score; }

    @Override
    public String toString() {
        return "学号：" + id + "| 姓名：" + name + "| 年龄：" + age + "| 成绩：" + score;
    }
}
