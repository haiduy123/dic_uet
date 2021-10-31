package mainScene;

import Alerts.Alerts;
import Dictionary.SQL;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

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
    private Button fixWord;

    @FXML
    private Button addButton;

    @FXML
    private Button translate;

    @FXML
    private Button closeBtn;

    @FXML
    private Button soundBtn;

    @FXML
    private Button check;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField engWord;

    @FXML
    private TextArea vnamWord;

    @FXML
    private Button toMeaning;

    Dictionary dictionary = new Dictionary();
    Word word = new Word();
    ArrayList<String> wordList = new ArrayList<>();
    private Alerts alerts = new Alerts();
    SQL myConnect = new SQL();

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

    public void addButton (ActionEvent event) throws Exception{
        URL url = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\FXML\\addWord.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        File f = new File("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Css\\mainStyle.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        Stage window = (Stage) ggTranslate.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void handleMouseClickAWord( MouseEvent arg0 ) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            engWord.setText(selectedWord);
        }
    }
    //cài đặt tra từ trong listview
    //nhập vào chữ nào thì hiện ra các từ bắt đầu bằng chữ đấy
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
//            DictionaryManagement.insertFromFile();
//            myConnect.connect();
            myConnect.addList();
            engWord.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!(engWord.getText().equals(null)) || engWord.getText().equals("")) {
                        boolean isNull = true;

                        listView.getItems().clear();
                        wordList.clear();
                        t1 = t1.trim();

                        String finalT = t1;

                        for (String w :myConnect.wordList) {
                            if (w.indexOf(t1) == 0) {
                                isNull = false;
                                break;
                            }
                        }

                        if (isNull == false) {
                            for (String w :myConnect.wordList) {
                                if (w.indexOf(finalT) == 0) {
                                    wordList.add(w);
                                }
                            }
                            int n = 0;
                            for (int i = 0; i < wordList.size(); i++) {
                                if (!listView.getItems().contains(wordList.get(i))) {
                                    listView.getItems().add(wordList.get(i));
                                    n++;
                                }
                                if (n == 15) {
                                    break;
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
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void soundBtn(ActionEvent event) {
        String word = engWord.getText();
        System.setProperty("freetts.voices" , "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(word);
        } else {
            throw new IllegalStateException("Wrong");
        }
    }
    // cài đặt nút button (dịch từ)
    public void toMeaning (ActionEvent event) throws Exception{
        String word = engWord.getText();
//        vnamWord.setText(DictionaryManagement.dictionaryLookup(word));
        myConnect.connect();
        vnamWord.setWrapText(true);
        vnamWord.setEditable(false);
        vnamWord.setText(myConnect.showData(myConnect.getWord(word)));
//        engWord.setText("");
        check.setVisible(false);
    }

    //chỉnh sửa nghĩa của từ
    public void fixWord (ActionEvent event){
        check.setVisible(true);
        vnamWord.setEditable(true);
        alerts.showAlertInfo("Information" , "Bạn đã cho phép chỉnh sửa nghĩa từ này!");
    }

    // sau khi chỉnh sửa thì dùng nút check để lưu lại nghĩa của từ
    public void check (ActionEvent event) {
            String newMeaning = vnamWord.getText();
            String word = engWord.getText();
            Alert alertConfirmation = alerts.alertConfirmation("Update" ,
                "Bạn chắc chắn muốn cập nhật nghĩa từ này ?");
        // option != null.
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            myConnect.fixWord(word, newMeaning);
            // successfully
            alerts.showAlertInfo("Information" , "Cập nhập thành công!");
        } else{
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận!");
        }
        // update status
        check.setVisible(false);
        vnamWord.setEditable(false);
    }

    //cài đặt nút xóa từ
    public void deleteButton (ActionEvent event) throws  Exception {
        String word = engWord.getText();
        Alert alertWarning = alerts.alertWarning("Delete" , "Bạn chắc chắn muốn xóa từ này?");
        // option != null.
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.get() == ButtonType.OK) {
            myConnect.connect();
//            DictionaryManagement.deleteWord(word);
            myConnect.deleteWord(word);
            alerts.showAlertInfo("Information" , "Xóa thành công");
        } else {
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận");
        }
        begin();
    }

    // trả textField và textArea về trạng thái ban đầu
    public void begin() {
        vnamWord.setText("");
        engWord.setText("");
    }

//    public static void main(String[] args) throws Exception {
//        SQL myConnect = new SQL();
//        myConnect.connect();
//        String tmp = myConnect.showData(myConnect.getWord("home"));
//        System.out.println(tmp);
//    }
}
