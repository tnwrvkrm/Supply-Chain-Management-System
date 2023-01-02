package com.example.supplychainmanagementsystem;

public class OrderAndCart {
    public static boolean placeOrder(String customerEmail, Product product){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("INSERT INTO orders (customer_id, product_id) VALUES((SELECT customer_id FROM customer WHERE email = '%s'), %s)",customerEmail, product.getId());
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }

    public static boolean addToCart(String customerEmail, int product_id){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("INSERT INTO cart VALUES((SELECT customer_id FROM customer WHERE email = '%s'), %s)",customerEmail, product_id);
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }

}
