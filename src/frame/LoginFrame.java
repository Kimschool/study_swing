package frame;

import util.DbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    public static String logIn = "admin";

    public LoginFrame() {

        // DB接続
        DbConn.getConnection();

        // タイトル、サイズ、閉じるボタンの設定
        setTitle("WEAVUS社員管理システム");

        // 大きさを指定
        setSize(300, 200);

        // FRAMEを画面中央に表示
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(3,1));

        JPanel p = new JPanel();
        JLabel l1 = new JLabel("ID");
        p.add(l1);

        JTextField t1 = new JTextField(10);
        p.add(t1);

        mainP.add(p);

        JPanel p1 = new JPanel();
        JLabel l2 = new JLabel("Password");
        JPasswordField t2 = new JPasswordField(10);
        p1.add(l2);
        p1.add(t2);

        mainP.add(p1);

        JPanel p2 = new JPanel();
        JButton b1 = new JButton("Login");
        JButton b2 = new JButton("SignIn");

        // ログインボタンのイベント
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = t1.getText();
                String password = new String(t2.getPassword());

                checkLogin(id, password);

            }
        });

        // 会員登録ボタンのイベント
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                new SignInFrame();
            }
        });

        p2.add(b1);
        p2.add(b2);

        mainP.add(p2);

        add(mainP);

        // 画面を表示
        setVisible(true);

    }

    private void checkLogin(String id, String password) {

        String sql = "select * from userinfo where id = ? and password = ?";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
               JOptionPane.showMessageDialog(null, "ログイン成功");
               // sessionにidを保持
               logIn = id;
               dispose();
               new MainFrame();
            } else {
                JOptionPane.showMessageDialog(null, "ログイン失敗");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }

}