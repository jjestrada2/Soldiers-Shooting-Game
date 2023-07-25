package tankrotationexample.menus;


import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourcesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

        JButton Controls = new JButton("Continue");
        Controls.setSize(new Dimension(200, 100));
        Controls.setFont(new Font("Courier New", Font.BOLD, 24));
        Controls.setBounds(140, 400, 200, 50);
        Controls.setFocusPainted(false);
        Controls.setBorderPainted(false);
        Controls.setBackground(new Color(0,0, 104));
        Controls.setForeground(Color.WHITE);
        Controls.addActionListener((actionEvent -> this.lf.setFrame("start")));

        this.add(Controls);
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
