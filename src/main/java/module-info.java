module com.example.supplychainmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.supplychainmanagementsystem to javafx.fxml;
    exports com.example.supplychainmanagementsystem;
}