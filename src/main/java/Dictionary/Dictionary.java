package Dictionary;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Dictionary {
    // map lưu trữ các từ Word để sắp xếp từ theo thứ tự từ a-z
    public static Map<String, String> Words = new TreeMap<>();

    public static void printAllWords() {
        int distant = 1;
        System.out.println("NO\t\t| ENGLISH\t| Vietnamese");
        for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
            System.out.println(distant + new Word(entry.getKey(), entry.getValue()).getWord());
            distant++;
        }
    }

    // chuyển từ đã nhập thành chữ thường và thêm vào Dictionary.Words nếu chưa có
    public static void addWord(String newWord, String mean) {
        Dictionary.Words.put(newWord.toLowerCase(), mean.toLowerCase());
    }

    // tìm từ trong Words
    public static Word findWord(String wordLook) {
        if (!Words.containsKey(wordLook)) {
            System.out.println("Không có từ cần tìm!");
            return new Word();
        } else
            return new Word(wordLook, Words.get(wordLook));
    }

    public static  void print () throws  Exception{
        DictionaryManagement.insertFromFile();
        for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}