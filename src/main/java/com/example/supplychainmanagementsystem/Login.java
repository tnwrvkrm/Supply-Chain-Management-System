package com.example.supplychainmanagementsystem;

import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login {

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
}
