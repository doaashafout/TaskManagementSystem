/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Doaa
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent p1 = FXMLLoader.load(getClass().getResource("../views/Menu.fxml"));
        Parent p2 = FXMLLoader.load(getClass().getResource("../views/Main.fxml"));

        BorderPane root = new BorderPane();

        root.setTop(p1);
        root.setCenter(p2);

        Scene scene = new Scene(root, 1000, 650);

        stage.setScene(scene);
        stage.setTitle("Task Management System");
        stage.show();
    }}
