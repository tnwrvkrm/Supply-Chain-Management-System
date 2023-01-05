package com.example.supplychainmanagementsystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public double getPrice() {
        return price.get();
    }

    public Product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Product> getAllProducts(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = "SELECT * FROM product";
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    public static ObservableList<Product> getProductsByName(String productName){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = String.format("SELECT * FROM product WHERE lower(name) like '%%%s%%'", productName.toLowerCase());
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    public static ObservableList<Product> getCartItems(String customerEmail){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = String.format("SELECT cart.product_id, product.name, product.price FROM cart JOIN product ON cart.product_id = product.product_id JOIN customer ON cart.customer_id = customer.customer_id\n" +
                "WHERE customer.email = '%s' group by product.product_id", customerEmail);
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }
}
