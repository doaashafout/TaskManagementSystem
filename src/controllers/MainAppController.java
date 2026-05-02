/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Task;

public class MainAppController implements Initializable {

    @FXML
    private TextField titleField;

    @FXML
    private TextField addedByField;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> operationBox;

    @FXML
    private TextField searchUserField;

    @FXML
    private ListView<Task> listView;

    @FXML
    private Label taskNumber;

    Map<Integer, Task> tasksMap = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        statusCombo.getItems().addAll("open", "closed");

        operationBox.getItems().addAll(
                "Show Tasks Added By User",
                "Earliest Four Tasks",
                "Titles Start With A And 7 Letters",
                "Most Active Contributor",
                "Count Open And Closed Tasks",
                "Count Tasks Added By User"
        );

        loadTasksFromCSV();
        refreshList();
    }

    

    

    @FXML
    private void addTaskHandle(ActionEvent event) {

        String title = titleField.getText();
        String status = statusCombo.getValue();
        String addedBy = addedByField.getText();
        LocalDate date = datePicker.getValue();

        if (title == null || title.trim().isEmpty()) {
            showAlert("Invalid Input", "Task title must not be empty.");
            return;
        }

        if (status == null || !(status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed"))) {
            showAlert("Invalid Input", "Status must be either open or closed.");
            return;
        }

        if (addedBy == null || addedBy.trim().isEmpty()) {
            showAlert("Invalid Input", "Added by field must not be empty.");
            return;
        }

        if (date == null) {
            showAlert("Invalid Input", "Please select a valid creation date.");
            return;
        }

        int newId = tasksMap.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(0) + 1;

        Task task = new Task(newId, title, status, addedBy, date);

        tasksMap.put(newId, task);
        saveTaskToCSV(task);
        refreshList();
        clearFields();
    }

    

    @FXML
    private void operationHandle(ActionEvent event) {

        if (operationBox.getValue() == null || operationBox.getValue().trim().isEmpty()) {
            return;
        }

        listView.getItems().clear();

        String operation = operationBox.getValue();

        if (operation.equalsIgnoreCase("Show Tasks Added By User")) {

            String user = searchUserField.getText();

            if (user == null || user.trim().isEmpty()) {
                showAlert("Invalid Input", "Please enter a user name.");
                refreshList();
                return;
            }

            tasksMap.values()
                    .stream()
                    .filter(task -> task.getAddedBy().equalsIgnoreCase(user))
                    .sorted(Comparator.comparing(Task::getId))
                    .forEach(task -> listView.getItems().add(task));

        } else if (operation.equalsIgnoreCase("Earliest Four Tasks")) {

            tasksMap.values()
                    .stream()
                    .sorted(Comparator.comparing(Task::getCreationDate))
                    .limit(4)
                    .forEach(task -> listView.getItems().add(task));

        } else if (operation.equalsIgnoreCase("Titles Start With A And 7 Letters")) {

            tasksMap.values()
                    .stream()
                    .filter(task -> task.getTitle().toLowerCase().startsWith("a"))
                    .filter(task -> task.getTitle().length() == 7)
                    .forEach(task -> listView.getItems().add(task));

        } else if (operation.equalsIgnoreCase("Most Active Contributor")) {

            String name =
                    tasksMap.values()
                            .stream()
                            .collect(Collectors.groupingBy(
                                    Task::getAddedBy,
                                    Collectors.counting()
                            ))
                            .entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .map(entry -> entry.getKey() + " added " + entry.getValue() + " tasks")
                            .orElse("No tasks found");

            listView.getItems().add(Task.createHeader(name));

        } else if (operation.equalsIgnoreCase("Count Open And Closed Tasks")) {

            long openCount =
                    tasksMap.values()
                            .stream()
                            .filter(task -> task.getStatus().equalsIgnoreCase("open"))
                            .count();

            long closedCount =
                    tasksMap.values()
                            .stream()
                            .filter(task -> task.getStatus().equalsIgnoreCase("closed"))
                            .count();

            listView.getItems().add(Task.createHeader("Open Tasks: " + openCount));
            listView.getItems().add(Task.createHeader("Closed Tasks: " + closedCount));

        } else if (operation.equalsIgnoreCase("Count Tasks Added By User")) {

            String user = searchUserField.getText();

            if (user == null || user.trim().isEmpty()) {
                showAlert("Invalid Input", "Please enter a user name.");
                refreshList();
                return;
            }

            long count =
                    tasksMap.values()
                            .stream()
                            .filter(task -> task.getAddedBy().equalsIgnoreCase(user))
                            .count();

            listView.getItems().add(Task.createHeader(user + " added " + count + " tasks"));
        }
    }

    @FXML
    private void showAllHandle(ActionEvent event) {
        operationBox.setValue("");
        searchUserField.clear();
        refreshList();
    }

    

    
    // helper function
    
    private void loadTasksFromCSV() {
        try {
            tasksMap =
                    Files.lines(Paths.get("src/data/tasks.csv"))
                            .skip(1)
                            .map(line -> line.split(","))
                            .map(data -> new Task(
                                    Integer.parseInt(data[0]),
                                    data[1],
                                    data[2],
                                    data[3],
                                    LocalDate.parse(data[4])
                            ))
                            .collect(Collectors.toMap(
                                    Task::getId,
                                    task -> task
                            ));

        } catch (IOException ex) {
            showAlert("File Error", "Could not load tasks from CSV file.");
        }
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void clearFields() {
        titleField.clear();
        addedByField.clear();
        statusCombo.setValue("");
        datePicker.setValue(null);
    }
    
    private void saveTaskToCSV(Task task) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/tasks.csv", true));

            bw.newLine();
            bw.write(task.getId() + "," +
                    task.getTitle() + "," +
                    task.getStatus() + "," +
                    task.getAddedBy() + "," +
                    task.getCreationDate());

            bw.close();

        } catch (IOException ex) {
            showAlert("File Error", "Could not save task to CSV file.");
        }
    }
    
    private void refreshList() {
        listView.getItems().clear();

        if (tasksMap != null) {
            listView.getItems().addAll(tasksMap.values());
            taskNumber.setText(tasksMap.size() + " tasks");
        }
    }
    
}


