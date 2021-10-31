package ggTranslate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Controller {

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane mainRoot;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField engWord;

    @FXML
    private Label vnamWord;

    @FXML
    private Button addButton;

    @FXML
    private Button ggTranslate;

    @FXML
    private Button Translate;


    File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
    @FXML

    //chuyển sng giao diện chính
    void searchButton(ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\mainScene.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) searchButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void addButton (ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\addWord.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) addButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }


    //cài đặt nút kết thúc chương trình
    @FXML
    public void Exit (ActionEvent event) {
        System.exit(0);
    }


    // cài đặt núy dịch văn bẳng sử dụng gg translate api
    @FXML
    void Translate(ActionEvent event) throws Exception{
        String word = engWord.getText();
        String output = API.translate(word);
        vnamWord.setText(output);
    }

}

