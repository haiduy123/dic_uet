package mainScene;

import Alerts.Alerts;
import Dictionary.Dictionary;
import Dictionary.DictionaryManagement;
import Dictionary.Word;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private Button ggTranslate;

    @FXML
    private Button addButton;

    @FXML
    private Button translate;

    @FXML
    private Button closeBtn;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField engWord;

    @FXML
    private Label vnamWord;

    @FXML
    private Button toMeaning;

    Dictionary dictionary = new Dictionary();
    Word word = new Word();
    ArrayList<String> wordList = new ArrayList<>();
    private Alerts alerts = new Alerts();


    // cài đặt nút kết thúc chương trình
    public void Exit (ActionEvent event) {
        System.exit(0);
    }

    private Stage stage;
    private Scene scene;
    private Parent root;


    //chuyển sang giao diện gg translate
    public void ggTranslate (ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\ggTranslate.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) ggTranslate.getScene().getWindow();
        window.setScene(new Scene(root));
    }


    //cài đặt tra từ trong listview
    //nhập vào chữ nào thì hiện ra các từ bắt đầu bằng chữ đấy
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DictionaryManagement.insertFromFile();
            engWord.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!(engWord.getText().equals(null)) || engWord.getText().equals("")) {
                        boolean isNull = true;

                        listView.getItems().clear();
                        wordList.clear();
                        t1 = t1.trim();

                        String finalT = t1;

                        for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
                            if (entry.getKey().indexOf(t1) == 0) {
                                isNull = false;
                                break;
                            }
                        }

                        if (isNull == false) {
                            for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
                                if (entry.getKey().indexOf(finalT) == 0) {
                                    wordList.add(entry.getKey());
                                }
                            }

                            for (int i = 0; i < wordList.size(); i++) {
                                if (!listView.getItems().contains(wordList.get(i))) {
                                    listView.getItems().add(wordList.get(i));
                                }
                            }
                        } else {
                            listView.getItems().add("Từ không tồn tại");
                        }
                    } else {
                        listView.getItems().clear();
                    }
                }
            });
        } catch (IOException ex) {
            ex.getMessage();
        }
    }


    // cài đặt nút button (dịch từ)
    public void toMeaning (ActionEvent event) throws Exception{
        String word = engWord.getText();
        vnamWord.setText(DictionaryManagement.dictionaryLookup(word));
    }

    //cài đặt nút xóa từ
    public void deleteButton (ActionEvent event) throws  Exception {
        String word = engWord.getText();
        Alert alertWarning = alerts.alertWarning("Delete" , "Bạn chắc chắn muốn xóa từ này?");
        // option != null.
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.get() == ButtonType.OK) {
            DictionaryManagement.deleteWord(word);
            alerts.showAlertInfo("Information" , "Xóa thành công");
        } else {
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận");
        }
    }
}
