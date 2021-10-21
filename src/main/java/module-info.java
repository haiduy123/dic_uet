module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens mainScene to javafx.fxml;
    exports mainScene;

    opens ggTranslate to javafx.fxml;
    exports ggTranslate;

}