package com.example.Interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomePage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sports Application");

        // Background Image
        Image backgroundImage = new Image("file:src/images/Gymwallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundStyle = new Background(background);

        // Logo Image
        Image logoImage = new Image("file:logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200);
        logoImageView.setPreserveRatio(true);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Sports App");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        welcomeText.setFill(Color.WHITE);

        // Description Text
        Text descriptionText = new Text("“Train like an athlete, eat like a nutritionist, sleep like a baby, win like a champion”");
        descriptionText.setFont(Font.font("Arial", 20));
        descriptionText.setFill(Color.WHITE);
        descriptionText.setWrappingWidth(400);
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Go to Users Button
        Button goToUsersButton = new Button("Start");
        goToUsersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        goToUsersButton.setPrefSize(100, 40);
        goToUsersButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        goToUsersButton.setOnAction(e -> {
            UsersInterface usersInterface = new UsersInterface();
            try {
                usersInterface.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Layout Setup
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(backgroundStyle);
        layout.getChildren().addAll(logoImageView, welcomeText, descriptionText, goToUsersButton);
        layout.setPadding(new Insets(50));

        // Scene Setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
