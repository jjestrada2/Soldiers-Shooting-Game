package warzone.menus;


import warzone.Launcher;
import warzone.loaders.ResourcesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SingInMenuPanel extends JPanel {
     private BufferedImage signInBackground;
     private final Launcher lf;

    public SingInMenuPanel(Launcher lf) {
        this.lf = lf;
        signInBackground = ResourcesManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JLabel username1Label = new JLabel("Username 1");
        username1Label.setFont(new Font("Courier New", Font.BOLD, 24));
        username1Label.setBounds(50, 200, 150, 50);
        username1Label.setForeground(Color.WHITE);

        JLabel username2Label = new JLabel("Username 2");
        username2Label.setFont(new Font("Courier New", Font.BOLD, 24));
        username2Label.setBounds(50, 300, 150, 50);
        username2Label.setForeground(Color.WHITE);

        JTextField username1Field = new JTextField();
        username1Field.setBounds(200, 200, 150, 50);
        username1Field.setSize(new Dimension(200, 50));
        username1Field.setFont(new Font("Courier New", Font.BOLD, 24));

        JTextField username2Field = new JTextField();
        username2Field.setBounds(200, 300, 150, 50);
        username2Field.setSize(new Dimension(200, 50));
        username2Field.setFont(new Font("Courier New", Font.BOLD, 24));

        JButton ContinueBtn = new JButton("Continue");
        ContinueBtn.setSize(new Dimension(200, 100));
        ContinueBtn.setFont(new Font("Courier New", Font.BOLD, 24));
        ContinueBtn.setBounds(140, 400, 200, 50);
        ContinueBtn.setFocusPainted(false);
        ContinueBtn.setBorderPainted(false);
        ContinueBtn.setBackground(new Color(0,0, 104));
        ContinueBtn.setForeground(Color.WHITE);
        ContinueBtn.addActionListener((actionEvent ->{
            //connection to mysql
            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName);
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?autoReconnect=true&useSSL=false","root","1234");

                this.lf.setFrame("start");
                //con.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }));

        this.add(ContinueBtn);
        this.add(username1Field);
        this.add(username2Field);
        this.add(username1Label);
        this.add(username2Label);



    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.signInBackground, 0, 0, null);
    }
}
