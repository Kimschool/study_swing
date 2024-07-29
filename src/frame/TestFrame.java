package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestFrame extends JFrame {

    public TestFrame() {

        setTitle("WEAVUS");
        setSize(300, 200); // px
        setLocationRelativeTo(null);

        setLayout(new GridLayout(2,1));

        JPanel p1 = new JPanel(new BorderLayout());
        JButton b1 = new JButton("Button 1");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Button 1 clicked");
            }
        });

        p1.add(b1, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        JButton b2 = new JButton("Button 2");

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainFrame();
            }
        });
        p2.add(b2, BorderLayout.CENTER);

        add(p1);
        add(p2);

        setVisible(true);




    }

    public static void main(String[] args) {
        new TestFrame();
    }



}
