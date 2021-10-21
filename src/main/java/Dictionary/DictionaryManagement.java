package Dictionary;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {
    /*
     * nhập vào số từ muốn thêm và thêm các từ đấy vào Dictionary.Words
     */
    public static void insertFromCommandline() throws Exception{

        Scanner sc = new Scanner(System.in);

        System.out.println("Nhap so tu muon them: ");
        int numberOfNewWord;
        numberOfNewWord = sc.nextInt();

        for (int i = 0; i < numberOfNewWord; i++) {
            String newWord = "";
            String meaning = "";
            System.out.println("Nhap tu:");
            newWord = sc.next();

            System.out.print("So nghia: ");
            int numberOfMeaning = sc.nextInt();
            System.out.println("Nhap nghia 1: ");
            meaning = meaning + "- " + sc.next();
            for (int j = 1; j < numberOfMeaning; j++) {
                System.out.println("Nhap nghia " + (j + 1) + ": ");
                meaning = meaning + "; " + "- " + sc.next();
            }
            Dictionary.addWord(newWord, meaning);
        }
    }

    /*
     * thêm từ từ file "dictionaris.txt"
     * nhận từ và nghĩa từ file -> tách từ và nghĩa -> thêm vào Dictionary.Words
     */
    public static void insertFromFile() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Txt\\data.txt");
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String line = br.readLine();
        while (line != null) {
            String s = "" + line;
            String meaning = br.readLine();
            for (; ; ) {
                line = br.readLine();
                if (line == null || line.charAt(0) != '-') break;
                meaning = meaning + "; " + line;
            }
            //System.out.println(s + "->" + meaning);
            Dictionary.addWord(s, meaning);
        }
    }

    /*
     * xuất dữ liệu ra file "dictionaris.txt"
     */
    public static void dictionaryExportToFile() throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\duyhai\\IdeaProjects\\demo2\\src\\main\\resources\\Txt\\data.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("\n");
        for (Map.Entry<String, String> entry : Dictionary.Words.entrySet()) {
            String[] meaning = entry.getValue().split("; ");
            String line = entry.getKey();
            bw.write(line);
            bw.newLine();
            for (String s : meaning) {
                bw.write(s);
                bw.newLine();
            }
        }
        bw.close();
        fw.close();
    }

    // sua tu dien
    public static void fixWord() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Nhap tu can sua: ");
        String word = sc.next();

        System.out.print("So nghia: ");
        int numberOfMeaning = sc.nextInt();

        String meaning = "";
        System.out.println("Nhap nghia 1: ");
        meaning = meaning + "- " + sc.next();
        for (int j = 1; j < numberOfMeaning; j++) {
            System.out.println("Nhap nghia " + (j + 1) + ": ");
            meaning = meaning + "; " + "- " + sc.next();
        }
        Dictionary.addWord(word, meaning);
    }

    //xóa từ
    public static void deleteWord(String word) {

        Scanner sc = new Scanner(System.in);

//        String word = "";
//
//        System.out.println("Nhap tu can xoa: ");
//        word = sc.next();

        Dictionary.Words.remove(word);
    }

    public static String dictionaryLookup(String wordLook) {

        Scanner sc = new Scanner(System.in);

//        System.out.println("Nhap tu can tim: ");
//        String wordLook = "";
//        wordLook = sc.next();
        String result = "";
        result += Dictionary.findWord(wordLook).getWord();
        return result;
    }

//    public static void main(String[] args) throws Exception{
//        DictionaryManagement dicm = new DictionaryManagement();
//        dicm.insertFromFile();
//    }
}