package data;

import java.sql.*;

public class DbConn {

    public static void main(String[] args) {

        // url
        String url = "jdbc:mysql://localhost:3306/test";
        // id
        String id = "root";
        // pw
        String pw = "";

        // Connection
        // DBに接続するためのクラス
        // URL,ID,PW利用し、dbに接続する
        Connection conn = null;

        // Statement
        // SQLを実行するためのクラス
        // Connectionを利用して、Statementを生成する
        Statement st = null;

        // ResultSet
        // SQLの実行結果を保持するクラス
        // Statementを利用して、ResultSetを生成する
        ResultSet rs = null;

        try {
            // DBの指定
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            String sql = "select count(*) as count from userinfo where id = ? and pw = ?";

            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                if(rs.getInt("count") == 1) {

                } else {

                }
                System.out.println(rs.getInt("id"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
