module com.example.mongocrud_v3_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.example.mongocrud_v3_ to javafx.fxml;
    exports com.example.mongocrud_v3_;
}