package com.example.demo;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private double averageGrade;

    public Student(String name, int age, double averageGrade) {
        this.name = name;
        this.age = age;
        this.averageGrade = averageGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public void displayInfo() {
        System.out.println("Ім'я: " + name + ", Вік: " + age + ", Середній бал: " + averageGrade);
    }

    //@Override
   // public String toString() {
   //     return "Ім'я: " + name + ", Вік: " + age + ", Середній бал: " + averageGrade;
   // }
}