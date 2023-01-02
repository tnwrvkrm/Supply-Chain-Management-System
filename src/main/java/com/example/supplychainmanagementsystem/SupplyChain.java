package com.example.supplychainmanagementsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyChain extends Application {

    public static final int width =700, height=600, headerBar=50;
    Pane bodyPane = new Pane();
    Login login = new Login();
    ProductDetails productDetails = new ProductDetails();
    Button globalLoginButton;
    Button globalLogoutButton;
    Label customerEmailLabel = null;
    String customerEmail = null;

    private GridPane headerBar(){
        TextField searchfield = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchfield.getText();
                //clear body pane first to put searched item
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });
        globalLoginButton = new Button("Log In");
        globalLogoutButton = new Button("Log Out");
        globalLogoutButton.setVisible(false);
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

        customerEmailLabel = new Label("Welcome User");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.add(searchfield,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(globalLoginButton,2,0);
        gridPane.add(globalLogoutButton,2,0);
        gridPane.add(customerEmailLabel,3,0);

        return gridPane;
    }

    //this is creating a grid structured layout
    private GridPane loginPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");

        TextField emailTextField = new TextField();
        PasswordField passwordTextfield = new PasswordField();
        Button loginButton = new Button("Login");

        globalLogoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                globalLoginButton.setVisible(true);
                globalLogoutButton.setVisible(false);
                customerEmailLabel.setText("Welcome User");
                customerEmail = null;
            }
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordTextfield.getText();
                String tempCustomerName = login.customerLogin(email,password);
                if(tempCustomerName != null) {
                    dialogBox("Login Success");
                    customerEmail = email;
                    globalLoginButton.setVisible(false);
                    globalLogoutButton.setVisible(true);
                    customerEmailLabel.setText("Welcome " + tempCustomerName);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());
                }
                else
                    dialogBox("Login Failed");
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

        return gridPane;
    }

    private GridPane footerBar(){
        Button addToCartButton = new Button("Add to Cart");
        Button goToCartButton = new Button("Go to Cart");
        Button buyNowButton = new Button("Buy Now");
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct = productDetails.getSelectedProduct();
                if(selectedProduct != null && OrderAndCart.placeOrder(customerEmail, selectedProduct)){
                    dialogBox("Order Placed");
                }
                else if(customerEmail == null){
                    dialogBox("You need to be logged In to make a order");
                }
                else{
                    dialogBox("Please select a item");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(customerEmail != null){
                    Product selectedProduct = productDetails.getSelectedProduct();
                    if(selectedProduct != null && OrderAndCart.addToCart(customerEmail, selectedProduct.getId())){
                        dialogBox("Item added");
                    }
                } else{
                    dialogBox("You need to LogIn first and then try adding to cart");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
            }
        });

        goToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(customerEmail != null){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getCartItems(customerEmail));
                }
                else{
                    dialogBox("LogIn to view your cart");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setTranslateY(headerBar+height+5);

        gridPane.add(addToCartButton,0,0);
        gridPane.add(goToCartButton,1,0);
        gridPane.add(buyNowButton,2,0);

        return gridPane;
    }

    public static void dialogBox(String message){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Buyer Shop");
        ButtonType button = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(button);
        dialog.showAndWait();
    }

    //this is creating the main window
     private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width,height+2*headerBar);

        bodyPane.setMinSize(width,height);
        bodyPane.setTranslateY(headerBar);

        bodyPane.getChildren().addAll(productDetails.getAllProducts());

        // this is adding the grid structured window to main window
        root.getChildren().addAll(headerBar(),bodyPane, footerBar());
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Buyer's Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}