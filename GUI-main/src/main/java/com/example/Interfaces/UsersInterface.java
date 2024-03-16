package com.example.Interfaces;

import Models.Users;
import Services.ServiceUsers;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UsersInterface extends Application {

    private ServiceUsers serviceUsers;

    private ObservableList<Users> userList;

    private TextField userNameField;
    private TextField emailField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serviceUsers = new ServiceUsers();

        primaryStage.setTitle("User Management");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        GridPane.setConstraints(userNameField, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        GridPane.setConstraints(emailField, 1, 1);

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 1, 2);
        addButton.setOnAction(e -> addUser());

        // Button refreshButton = new Button("Refresh");
        // GridPane.setConstraints(refreshButton, 0, 3);
        // refreshButton.setOnAction(e -> refreshUsers());

        TableView<Users> table = new TableView<>();
        userList = FXCollections.observableArrayList();
        table.setItems(userList);

        TableColumn<Users, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameDBProperty());
        table.getColumns().add(nameColumn);

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().userEmailDBProperty());
        table.getColumns().add(emailColumn);

        GridPane.setConstraints(table, 0, 4, 2, 1);

        grid.getChildren().addAll(nameLabel, userNameField, emailLabel, emailField, addButton, table);
        // grid.getChildren().addAll(nameLabel, userNameField, emailLabel, emailField, addButton, refreshButton, table);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial load of users
        // refreshUsers();
    }

    private void addUser() {
        String username = userNameField.getText();
        String email = emailField.getText();

        Users newUser = new Users(username, email, "member");

        try {
            serviceUsers.add(newUser);
            userList.add(newUser);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding user", e.getMessage());
        }
    }

    private void refreshUsers() {
        userList.clear();
        try {
            userList.addAll(serviceUsers.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching users", e.getMessage());
        }
    }

    private void clearFields() {
        userNameField.clear();
        emailField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

