package frame;

import util.DbConn;
import vo.UserInfoVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeFrame extends JFrame {
    public final static String MALE = "0";
    public final static String FEMALE = "1";
    JTextField t1 = new JTextField(10);
    JPasswordField t2 = new JPasswordField(10);
    JPasswordField t3 = new JPasswordField(10);
    JTextField t4 = new JTextField(10);
    JRadioButton r1 = new JRadioButton("男");
    JRadioButton r2 = new JRadioButton("女");
    JTextField t6 = new JTextField(3);
    JTextField t7 = new JTextField(10);

    public ChangeFrame() {
        setTitle("WEAVUS社員管理システム");
        setSize(300, 250);
        setLocationRelativeTo(null);

        // Dbからユーザー情報を取得する。
        UserInfoVo userInfoVo = findUserInfo();

        JPanel mainP = new JPanel(new GridLayout(8,1));

        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("ID");
        t1.setText(userInfoVo.getId());

        p1.add(l1);
        p1.add(t1);

        JPanel p2 = new JPanel();
        JLabel l2 = new JLabel("Password");
        p2.add(l2);
        p2.add(t2);

        JPanel p3 = new JPanel();
        JLabel l3 = new JLabel("Password(確認)");
        p3.add(l3);
        p3.add(t3);

        JPanel p4 = new JPanel();
        JLabel l4 = new JLabel("名前");
        t4.setText(userInfoVo.getName());
        p4.add(l4);
        p4.add(t4);

        JPanel p5 = new JPanel();
        JLabel l5 = new JLabel("性別");

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        p5.add(l5);
        p5.add(r1);
        p5.add(r2);

        if(userInfoVo.getGender().equals(MALE)) {
            r1.setSelected(true);
        } else {
            r2.setSelected(true);
        }

        JPanel p6 = new JPanel();
        JLabel l6 = new JLabel("年齢");

        // int -> String 変換
        String convAge = Integer.toString(userInfoVo.getAge());

        t6.setText(convAge);
        p6.add(l6);
        p6.add(t6);

        JPanel p7 = new JPanel();
        JLabel l7 = new JLabel("生年月日");
        t7.setText(userInfoVo.getBirthDay());
        p7.add(l7);
        p7.add(t7);

        JPanel p8 = new JPanel();
        JButton b1 = new JButton("クリア");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                bg.clearSelection();
                t6.setText("");
                t7.setText("");
            }
        });

        JButton b2 = new JButton("変更");

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // エラーがなかったら、更新
                // エラーがあったら、更新しない
                if(!fieldCheck()) {
                    updateUserInfo();
                }

            }
        });

        JButton b3 = new JButton("戻り");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame();
            }
        });

        p8.add(b1);
        p8.add(b2);
        p8.add(b3);


        mainP.add(p1);
        mainP.add(p2);
        mainP.add(p3);
        mainP.add(p4);
        mainP.add(p5);
        mainP.add(p6);
        mainP.add(p7);
        mainP.add(p8);

        add(mainP);

        setVisible(true);
    }

    private UserInfoVo findUserInfo() {

        String sql = "select * from userinfo where id = ?";
        //　ResultSet  -> UserInfoVo変換
        UserInfoVo userInfoVo = new UserInfoVo();

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            ps.setString(1, LoginFrame.logIn);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userInfoVo.setId(rs.getString("id"));
                userInfoVo.setPw(rs.getString("password"));
                userInfoVo.setName(rs.getString("name"));
                userInfoVo.setGender(rs.getString("gender"));
                userInfoVo.setAge(rs.getInt("age"));
                userInfoVo.setBirthDay(rs.getString("birthday"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfoVo;
    }

    private boolean fieldCheck() {
        // 1. 各項目の入力チェック
        boolean isError = false;

        if(t1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "IDを入力してください");
            isError = true;
        } else if(new String(t2.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Passwordを入力してください");
            isError = true;
        } else if(new String(t3.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password(確認)を入力してください");
            isError = true;
        } else if(!new String(t2.getPassword()).equals(new String(t3.getPassword()))) {
            JOptionPane.showMessageDialog(null, "Passwordが一致しません。");
            isError = true;
        } else if(t4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "名前を入力してください");
            isError = true;
        } else if(!r1.isSelected() && !r2.isSelected()) {
            JOptionPane.showMessageDialog(null, "性別を選択してください");
            isError = true;
        } else if(t6.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "年齢を入力してください");
            isError = true;
        } else if(t7.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "生年月日を入力してください");
            isError = true;
        }
        return isError;
    }

    private void updateUserInfo() {

        String sql = "update userinfo set password = ?, name =?, gender=?, age=?, birthday=? where id = ?";

        try {
            PreparedStatement ps = DbConn.conn.prepareStatement(sql);
            // PW
            ps.setString(1, new String(t2.getPassword()));
            // 名前
            ps.setString(2, t4.getText());
            // 性別
            if(r1.isSelected()) {
                ps.setString(3, MALE);
            } else {
                ps.setString(3, FEMALE);
            }
            // 年齢
            ps.setInt(4, Integer.parseInt(t6.getText()));
            // 生年月日
            ps.setString(5, t7.getText());
            // ID
            ps.setString(6, t1.getText());

            int result = ps.executeUpdate();

            if(result == 1) {
                JOptionPane.showMessageDialog(null, "更新完了");
            } else {
                JOptionPane.showMessageDialog(null, "更新失敗");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
