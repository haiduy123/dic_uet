package Dictionary;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.io.IOException;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;


public class SQL {

    /**
     * url: dataname
     */
    public String url = "jdbc:mysql://localhost:3306/data";
    public String username = "root";
    public String password = "haiduy39";
    public String table = "tbl_edict";
    public Connection connection;
    public static List<String> wordList = new ArrayList<>();
    public int num;


    public void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            num = getNumber();
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public int getNumber() {
        int n = 0;
        ResultSet rs = null;
        String sqlCommand = "SELECT * FROM " + table + " ORDER BY idx DESC LIMIT 1";
        Statement st;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sqlCommand);
            rs.next();
            n = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Select error");
            e.printStackTrace();
        }
        return n;

    }

    public String showData(ResultSet rs) throws SQLException {
        String tmp = "";
        while (rs.next()) {
            tmp += rs.getString(3);
//            wordList.add(rs.getString(3));
//            System.out.printf("%-10s %-20s %-20s \n", rs.getString(1), rs.getString(2), rs.getString(3));
        }
        return tmp;
    }

    /**
     * trả về tất cả các từ.
     */
    public ResultSet getAllWord() {
        ResultSet rs = null;
        String sqlCommand = "select * from " + table;
        Statement st;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sqlCommand);
        } catch (SQLException e) {
            System.out.println("Select error");
            e.printStackTrace();
        }
        return rs;
    }

    public void addList() throws SQLException{
        connect();
        ResultSet rs = getAllWord();
        while (rs.next()) {
            wordList.add(rs.getString(2));
        }
    }

    /**
     * Trả về từ cần tìm.
     */
    public ResultSet getWord(String word_target) {
        ResultSet rs = null;
        String sqlCommand = "select * from " + table + " where word = ?";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, word_target);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("Select error");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * xóa từ.
     */
    public void deleteWord(String word_target) {
        String sqlCommand = "delete from " + table + " where word = ?";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setString(1, word_target);
            if (pst.executeUpdate() > 0) {
                System.out.println("Đã xóa");
                num--;
                wordList.remove(word_target);
            } else {
                System.out.println("Không có từ cần xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * thêm từ.
     */
    public void insert(String newWord, String meaning) {
        String sqlCommand = "insert into " + table + " value(?, ?, ?)";
        PreparedStatement pst = null;
        wordList.add(newWord);
        try {
            pst = connection.prepareStatement(sqlCommand);
            pst.setInt(1, ++num );
            pst.setString(2, newWord);
            pst.setString(3, meaning);
            if (pst.executeUpdate() > 0) {
                System.out.println("Thêm từ thành công: " + meaning);
            } else {
                System.out.println("Chưa thể thêm từ!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sửa từ.
     */
//    public void updateId(String word_target, Word s) {
//        String sqlCommand = "update " + table + " set word = ?, detail = ? where word = ?";
//        PreparedStatement pst = null;
//        try {
//            pst = connection.prepareStatement(sqlCommand);
//            pst.setString(1, s.word_target);
//            pst.setString(2, s.word_explain);
//            pst.setString(3, word_target);
//
//            if (pst.executeUpdate() > 0) {
//                System.out.println("update success :" + pst.executeUpdate());
//            } else {
//                System.out.println("update error");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void fixWord(String word, String meaning) {
        deleteWord(word);
        insert(word, meaning);
    }
    public static void main(String[] args) throws SQLException {
        SQL myConnect = new SQL();
        myConnect.connect();
        myConnect.addList();
        for (String w : wordList) {
            System.out.println(w + "\n");
        }
//        int n = myConnect.getNumber();
//        System.out.println(n);
//        myConnect.insert("abcxyz1", "ghrjkdjkd");
//        String tmp  = "";
//        tmp += myConnect.showData(myConnect.getWord("abcxyz1"));
//        System.out.println(tmp);
    }
}
