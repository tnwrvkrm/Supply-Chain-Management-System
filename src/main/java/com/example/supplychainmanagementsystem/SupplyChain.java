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
    LoginOrSignUp loginAndSignUp = new LoginOrSignUp();
    ProductDetails productDetails = new ProductDetails();
    Button globalLoginButton;
    Button globalLogoutButton;
    Button globalSignUpButton;
    Label customerEmailLabel = null;
    String customerEmail = null;

    private GridPane headerBar(){
        TextField searchfield = new TextField();
        Button searchButton = new Button("Search");
        globalLoginButton = new Button("Log In");
        globalLogoutButton = new Button("Log Out");
        globalLogoutButton.setVisible(false);
        globalSignUpButton = new Button("Sign Up");
        globalLoginButton.setMinWidth(70);
        globalLogoutButton.setMinWidth(70);
        globalSignUpButton.setMinWidth(70);
        customerEmailLabel = new Label("Welcome User");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchfield.getText();
                //clear body pane first to put searched item
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

        globalLogoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                globalLoginButton.setVisible(true);
                globalLogoutButton.setVisible(false);
                globalSignUpButton.setVisible(true);
                customerEmailLabel.setText("Welcome User");
                customerEmail = null;
            }
        });

        globalSignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpPage());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #E6E6FA");

        gridPane.add(searchfield,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(globalLoginButton,2,0);
        gridPane.add(globalLogoutButton,2,0);
        gridPane.add(customerEmailLabel,3,0);
        gridPane.add(globalSignUpButton,4,0);

        return gridPane;
    }

    //this is creating a grid structured layout
    private GridPane loginPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");

        TextField emailTextField = new TextField();
        PasswordField passwordTextfield = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordTextfield.getText();
                String tempCustomerName = loginAndSignUp.customerLogin(email,password);
                if(tempCustomerName != null) {
                    dialogBox("Login Success");
                    customerEmail = email;
                    globalLoginButton.setVisible(false);
                    globalLogoutButton.setVisible(true);
                    globalSignUpButton.setVisible(false);
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
        gridPane.setStyle("-fx-background-color: #FFFAF0");

        gridPane.add(emailLabel,0,0);
        gridPane.add(passwordLabel, 0,1);
        gridPane.add(emailTextField, 1,0);
        gridPane.add(passwordTextfield, 1,1);
        gridPane.add(loginButton,0,2);

        return gridPane;
    }

    private GridPane signUpPage(){
        Label firstNameLabel = new Label("Given Name *");
        Label lastNameLabel = new Label("family Name");
        Label emailLabel = new Label("Email *");
        Label passwordLabel = new Label("Password *");
        Label mobileLabel = new Label("Mobile No");
        Label addressLabel = new Label("Address");
        Label mandatoryLabel = new Label("* marked are mandatory");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField passwordField = new TextField();
        TextField mobileField = new TextField();
        TextField addressField = new TextField();

        Button signUpButton = new Button("Sign Up");

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String mobile = mobileField.getText();
                String address = addressField.getText();

                if(firstName.equals("") || email.equals("") || password.equals("")){
                    dialogBox("Please fill all mandatory fields");
                }
                else if(loginAndSignUp.customerSignUp(firstName, lastName, email, password, mobile, address)){
                    dialogBox("SignUp Successful");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
                else{
                    dialogBox("Email Already registered");
                    loginPage();
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #FFFAF0");

        gridPane.add(firstNameLabel,0,0);
        gridPane.add(lastNameLabel,2,0);
        gridPane.add(emailLabel,0,1);
        gridPane.add(passwordLabel,2,1);
        gridPane.add(mobileLabel,0,2);
        gridPane.add(addressLabel,2,2);
        gridPane.add(mandatoryLabel,0,3);

        gridPane.add(firstNameField,1,0);
        gridPane.add(lastNameField,3,0);
        gridPane.add(emailField,1,1);
        gridPane.add(passwordField,3,1);
        gridPane.add(mobileField,1,2);
        gridPane.add(addressField,3,2);

        gridPane.add(signUpButton,2,3);

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
        gridPane.setStyle("-fx-background-color: #E6E6FA");
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