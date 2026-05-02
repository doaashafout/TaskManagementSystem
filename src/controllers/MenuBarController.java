/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;

public class MenuBarController implements Initializable {

    @FXML
    private MenuBar menubar;

    @FXML
    private RadioMenuItem small;

    @FXML
    private RadioMenuItem medium;

    @FXML
    private RadioMenuItem large;

    @FXML
    private RadioMenuItem arial;

    @FXML
    private RadioMenuItem times;

    @FXML
    private RadioMenuItem sans;

    @FXML
    private CheckMenuItem bold;

    @FXML
    private CheckMenuItem italic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void exitHandle(ActionEvent event) {
        ((Stage) menubar.getScene().getWindow()).close();
    }

    @FXML
    private void appearanceHandle(ActionEvent event) {

        String size = "14";

        if (small.isSelected()) {
            size = "12";
        } else if (medium.isSelected()) {
            size = "14";
        } else if (large.isSelected()) {
            size = "18";
        }

        String family = "Arial";

        if (arial.isSelected()) {
            family = "Arial";
        } else if (times.isSelected()) {
            family = "Times New Roman";
        } else if (sans.isSelected()) {
            family = "Sans Serif";
        }

        String weight = bold.isSelected() ? "bold" : "normal";
        String style = italic.isSelected() ? "italic" : "normal";

        menubar.getScene().getRoot().setStyle(
                "-fx-font-size: " + size + "px;" +
                "-fx-font-family: '" + family + "';" +
                "-fx-font-weight: " + weight + ";" +
                "-fx-font-style: " + style + ";"
        );
    }

    @FXML
    private void aboutHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Task Management System");
        alert.setContentText("Version: 1.0\nDeveloper: Doaa Shafout\nJavaFX Desktop Application");
        alert.showAndWait();
    }
}