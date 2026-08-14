package com.example.studentmanagementweb.model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int age;
    private double score;

    public Student (String id, String name, int age, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getScore() { return score; }
    public void setAge(int age) { this.age = age; }
    public int getAge() { return age; }
    public void setScore(double score) { this.score = score; }

    @Override
    public String toString() {
        return "学号：" + id + "| 姓名：" + name + "| 年龄：" + age + "| 成绩：" + score;
    }
}
