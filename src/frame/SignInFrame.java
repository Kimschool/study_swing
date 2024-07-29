package frame;

import util.DbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignInFrame extends JFrame {

    public SignInFrame() {

        setTitle("WEAVUS社員管理システム");
        setSize(300,200);
        setLocationRelativeTo(null);

        JPanel mainP = new JPanel(new GridLayout(4,0));

        // ID
        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("ID");
        JTextField t1 = new JTextField(10);
        p1.add(l1) ;
        p1.add(t1);

        // pw1
        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("Password");
        JPasswordField t2 = new JPasswordField(10);
        p2.add(l2);
        p2.add(t2);

        // pw2
        JPanel p3 = new JPanel();
        JLabel l3 = new JLabel("Password(確認)");
        JPasswordField t3 = new JPasswordField(10);
        p3.add(l3);
        p3.add(t3);

        // button
        JPanel p4 = new JPanel();
        JButton b1 = new JButton("戻り");
        JButton b2 = new JButton("登録");
        p4.add(b1);
        p4.add(b2);

        // 登録ボタンイベント
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 各項目の入力チェック
                boolean isError = false;

                String id = t1.getText();
                String pw1 = new String(t2.getPassword());
                String pw2 = new String(t3.getPassword());

                // ブランクチェック
                if(id.isEmpty() || pw1.isEmpty() || pw2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "全ての項目を入力してください");
                    isError = true;
                }

                // パスワード一致チェック
                if(!pw1.equals(pw2)) {
                    JOptionPane.showMessageDialog(null, "パスワードが一致しません。");
                    isError = true;
                }

                //　エラーがなかった場合、登録処理を行う
                if(!isError) {
                   saveUserInfo(id, pw1);
                }
            }
        });

        mainP.add(p1); mainP.add(p2); mainP.add(p3); mainP.add(p4);

        add(mainP);

        setVisible(true);

    }

    private void saveUserInfo(String id, String pw) {

        String sql = "insert into userinfo (id, password, name) values (? , ?, 'aaa')";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pw);

            int result = ps.executeUpdate();

            if(result == 1) {
                JOptionPane.showMessageDialog(null, "登録完了");
                dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(null, "登録失敗");
            }

        } catch (SQLIntegrityConstraintViolationException e1) {
            JOptionPane.showMessageDialog(null, "登録失敗");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new SignInFrame();
    }

}
