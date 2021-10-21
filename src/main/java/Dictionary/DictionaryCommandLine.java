package Dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommandLine {
    /*
     * in tất cả các từ trong Dictionary.Words.
     */
    public static void showAllWords() {
        Dictionary.printAllWords();
    }

    /*
     * gọi hàm DictionaryManagement.insertFromCommandline() và hàm showAllWords().
     * (thêm từ và in ra những từ có trong Dictionary.Words)
     */
    public static void dictionaryBasic() throws Exception{
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    /*
     * gọi hàm DictionaryManagement.insertFromFile() và hàm showAllWords() và DictionaryManagement.dictionaryLookup().
     * (thêm từ từ file và in ra những từ có trong Dictionary.Words và tìm kiếm từ)
     */
    public static void dictionaryAdvanced() throws IOException {
        DictionaryManagement.insertFromFile();
        showAllWords();
//        DictionaryManagement.dictionaryLookup();
    }

    /*
     * tìm kiếm từ hoặc 1 phần từ
     */
    public static List dictionarySearcher() {
        Scanner sc = new Scanner(System.in);

        List<String> listWord = new ArrayList<>();
        System.out.println("Nhap tu can tim: ");
        String wordLook = "";
        wordLook = sc.nextLine();
        for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
            String keyWord = entry.getKey();
            if (keyWord.contains(wordLook)) {
                listWord.add(entry.getKey());
                System.out.print(entry.getKey() + ", " );
            }
        }
        return listWord;
    }


    public static void main(String[] args) throws Exception {
        Dictionary.print();
    }
}