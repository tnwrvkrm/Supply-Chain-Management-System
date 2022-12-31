module com.example.supplychainmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.supplychainmanagementsystem to javafx.fxml;
    exports com.example.supplychainmanagementsystem;
}