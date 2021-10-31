package addWord;

import Alerts.Alerts;
import Dictionary.SQL;
import Dictionary.DictionaryManagement;
import Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class Controller {
    @FXML
    private Label addLabel;

    @FXML
    private Button searchButton;

    @FXML
    private TextField addNewWord;

    @FXML
    private TextField addMeaning;

    @FXML
    private AnchorPane mainRoot;

    @FXML
    private Button closeBtn;

    @FXML
    private Button addButton;

    @FXML
    private Button ggTranslate;

    @FXML
    private Button addNewBtn;


    public void Exit (ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void searchButton(ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\mainScene.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) searchButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void ggTranslate(ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\ggTranslate.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) ggTranslate.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    @FXML
    void addNewBtn(ActionEvent event) throws Exception {
//        String word = addNewWord.getText();
//        String meaning = addMeaning.getText();
//        DictionaryManagement.addWord(word,meaning);
        SQL myConnect = new SQL();
        myConnect.connect();

        Alert alertConfirmation = Alerts.alertConfirmation("Add word" ,
                "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        // get data from input
        String word = addNewWord.getText();
        String meaning = addMeaning.getText();

        if (option.get() == ButtonType.OK) {
            // find index of word in dictionary
            // show confirmation alert
            // custom button
            alertConfirmation.getButtonTypes().clear();
            ButtonType insertBtn = new ButtonType("Thêm");
            alertConfirmation.getButtonTypes().addAll(insertBtn , ButtonType.CANCEL);
            Optional<ButtonType> selection = alertConfirmation.showAndWait();

            if(selection.get() == insertBtn) {
//                Dictionary.addWord(word,meaning);
//                DictionaryManagement.dictionaryExportToFile();
                myConnect.insert(word, meaning);
//                myConnect.connect();
                Alerts.showAlertInfo("Information" , "Thay đổi được công nhận.");
            }
            if(selection.get() == ButtonType.CANCEL){
                Alerts.showAlertInfo("Information" , "Thay đổi không được công nhận.");
            }
        }
    }
}

