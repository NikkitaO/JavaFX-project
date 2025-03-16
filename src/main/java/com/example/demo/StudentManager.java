package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManager() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean removeStudent(String name) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getName().equals(name)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /* public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
            return;
        }

        System.out.println("Список студентів:");
        for (Student student : students) {
            student.displayInfo();
        }
    }

     */
    //метод вище виявився непотрібним

    public double calculateAverageGrade() {
        if (students.isEmpty()) {
            System.out.println("Неможливо розрахувати середній бал: список студентів порожній.");
            return 0;
        }

        double sum = 0;
        for (Student student : students) {
            sum += student.getAverageGrade();
        }

        double average = sum / students.size();
        System.out.println("Середній бал усіх студентів: " + average);
        return average;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Дані студентів збережено у файл " + FILE_NAME);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Файл " + FILE_NAME + " не знайдено. Створено новий список студентів.");
            this.students = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            this.students = (List<Student>) ois.readObject();
            System.out.println("Дані студентів завантажено з файлу " + FILE_NAME);
        }
    }
}