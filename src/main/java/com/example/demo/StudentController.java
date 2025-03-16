package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField gradeField;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, Integer> ageColumn;

    @FXML
    private TableColumn<Student, Double> gradeColumn;

    @FXML
    private Label averageLabel;

    private StudentManager studentManager;
    private ObservableList<Student> studentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentManager = new StudentManager();

        // Ініціалізація колонок таблиці
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("averageGrade"));
        try {
            studentManager.loadFromFile();
            updateTableView();
            calculateAverage();
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Помилка завантаження", "Не вдалося завантажити дані: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddStudent(ActionEvent event) {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double grade = Double.parseDouble(gradeField.getText());

            if (name.trim().isEmpty()) {
                showAlert("Помилка введення", "Ім'я не може бути порожнім");
                return;
            }

            if (age < 15 || age > 100) {
                showAlert("Помилка введення", "Вік повинен бути від 15 до 100");
                return;
            }

            if (grade < 0 || grade > 100) {
                showAlert("Помилка введення", "Середній бал повинен бути від 0 до 100");
                return;
            }

            Student student = new Student(name, age, grade);
            studentManager.addStudent(student);

            clearFields();
            updateTableView();
            calculateAverage();

        } catch (NumberFormatException e) {
            showAlert("Помилка введення", "Перевірте формат введення віку та середнього балу");
        }
    }

    @FXML
    private void handleRemoveStudent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean removed = studentManager.removeStudent(selectedStudent.getName());
            if (removed) {
                updateTableView();
                calculateAverage();
            }
        } else {
            showAlert("Не вибрано студента", "Виберіть студента для видалення");
        }
    }

    @FXML
    private void handleSaveData(ActionEvent event) {
        try {
            studentManager.saveToFile();
        } catch (IOException e) {
            showAlert("Помилка збереження", "Не вдалося зберегти дані: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoadData(ActionEvent event) {
        try {
            studentManager.loadFromFile();
            updateTableView();
            calculateAverage();
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Помилка завантаження", "Не вдалося завантажити дані: " + e.getMessage());
        }
    }

    private void calculateAverage() {
        double average = studentManager.calculateAverageGrade();
        averageLabel.setText(String.format("Середній бал: %.2f", average));
    }

    private void updateTableView() {
        studentList = FXCollections.observableArrayList(studentManager.getStudents());
        studentTable.setItems(studentList);
    }

    private void clearFields() {
        nameField.clear();
        ageField.clear();
        gradeField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}