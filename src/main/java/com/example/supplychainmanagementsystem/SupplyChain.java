package com.example.supplychainmanagementsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyChain extends Application {

    public static final int width =700, height=600, headerBar=50;
    Pane bodyPane = new Pane();

    private GridPane headerBar(){
        TextField searchfield = new TextField();
        Button searchButton = new Button("Search");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.add(searchfield,0,0);
        gridPane.add(searchButton,1,0);

        return gridPane;
    }

    //this is creating a grid structured layout
    private GridPane loginPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("Message");

        TextField emailTextField = new TextField();
        PasswordField passwordTextfield = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordTextfield.getText();
                messageLabel.setText(email+"$$"+password);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.add(emailLabel,0,0);
        gridPane.add(passwordLabel, 0,1);
        gridPane.add(emailTextField, 1,0);
        gridPane.add(passwordTextfield, 1,1);
        gridPane.add(loginButton,0,2);
        gridPane.add(messageLabel,1,2);

        return gridPane;
    }

    //this is creating the main window
     private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width,height+headerBar);

        bodyPane.setMinSize(width,height);
        bodyPane.setTranslateY(headerBar);

        bodyPane.getChildren().addAll(loginPage());

        // this is adding the grid structured window to main window
        root.getChildren().addAll(headerBar(),bodyPane);
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}