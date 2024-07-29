package frame;

import util.DbConn;
import vo.UserInfoJoinUserDetailVo;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListFrame extends JFrame {

    public ListFrame() {
        setTitle("WEAVUS社員管理システム");
        setSize(400, 500);
        setLocationRelativeTo(null);

        List<UserInfoJoinUserDetailVo> voList = findUserInfoJoinUserDetail();

        JPanel mainP = new JPanel(new GridLayout(3,1));

        JPanel p1 = new JPanel(new BorderLayout());
        JLabel l1 = new JLabel("件数：" + voList.size() + "件");
        p1.add(l1, BorderLayout.WEST);

        JPanel p2 = new JPanel();
        JTextArea ta = new JTextArea(10, 30);
        ta.setText("ID\t名前\t性別\t年齢\t給料\tステータス\n");

        for(UserInfoJoinUserDetailVo vo  : voList) {

            String gender;
            if(vo.getGender().equals("0")) {
                gender = "男性";
            } else {
                gender = "女性";
            }

            String jobStatus;
            if(vo.getJobStatus().equals("0")) {
                jobStatus = "在籍中";
            } else if(vo.getJobStatus().equals("1")) {
                jobStatus = "退職済み";
            } else {
                jobStatus = "その他";
            }
            ta.append(vo.getId()+"\t"+vo.getName()+"\t"+gender+"\t"+vo.getAge()+"\t"+vo.getSalary()+"\t"+jobStatus+"\n");
        }

        p2.add(ta);

        JPanel p3 = new JPanel();
        JButton b1 = new JButton("戻り");
        p3.add(b1);

        mainP.add(p1);
        mainP.add(p2);
        mainP.add(p3);

        add(mainP);

        setVisible(true);
    }

    private List<UserInfoJoinUserDetailVo> findUserInfoJoinUserDetail() {

        String sql = "SELECT i.id id, i.name as name, i.gender gender, i.age age, d.salary salary, d.job_status job_status from userinfo i inner join userdetail d on i.id = d.userid;";
        List<UserInfoJoinUserDetailVo> voList = new ArrayList<>();

        try {
            Statement st = DbConn.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                UserInfoJoinUserDetailVo vo = new UserInfoJoinUserDetailVo();
                vo.setId(rs.getString("id"));
                vo.setName(rs.getString("name"));
                vo.setGender(rs.getString("gender"));
                vo.setAge(rs.getInt("age"));
                vo.setSalary(rs.getInt("salary"));
                vo.setJobStatus(rs.getString("job_status"));
                voList.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return voList;
    }

    public static void main(String[] args) {
        DbConn.getConnection();
        new ListFrame();
    }
}
