module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires javafx.media;
    requires freetts;

    opens mainScene to javafx.fxml;
    exports mainScene;

    opens ggTranslate to javafx.fxml;
    exports ggTranslate;

    opens addWord to javafx.fxml;
    exports addWord;
}