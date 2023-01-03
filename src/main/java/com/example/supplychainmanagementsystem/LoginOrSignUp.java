package com.example.supplychainmanagementsystem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class LoginOrSignUp {

    private byte[] getSHA(String input){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getEncryptedPassword(String password){
        String encryptedPassword = "";
        try{
            BigInteger number = new BigInteger(1, getSHA(password));
            StringBuilder hexString = new StringBuilder(number.toString(16));
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String customerLogin(String email, String password){
        String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s'", email, password);
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs != null && rs.next()) {
                return rs.getString("first_name");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean customerSignUp(String firstName, String lastName, String email, String password, String mobile, String address){
        DatabaseConnection dbCon = new DatabaseConnection();
        String query = String.format("INSERT INTO CUSTOMER (first_name, last_name, email, password, mobile, address) values('%s','%s','%s','%s','%s','%s')", firstName, lastName, email, getEncryptedPassword(password), mobile, address);
        int rowCount = 0;
        try{
            rowCount = dbCon.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }
}
