package tankrotationexample.menus;


import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourcesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;

            menuBackground = ResourcesManager.getSprite("menu");

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //buttons
        JButton start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(50, 300, 150, 50);
        start.addActionListener(actionEvent -> this.lf.setFrame("game"));
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.setBackground(new Color(0, 0, 253));
        start.setForeground(Color.WHITE);

        JButton exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(250, 300, 150, 50);
        exit.setFocusPainted(false);
        exit.setBorderPainted(false);
        exit.setBackground(new Color(0,0, 104));
        exit.setForeground(Color.WHITE);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        JButton Controls = new JButton("Controls");
        Controls.setSize(new Dimension(200, 100));
        Controls.setFont(new Font("Courier New", Font.BOLD, 24));
        Controls.setBounds(140, 400, 200, 50);
        Controls.setFocusPainted(false);
        Controls.setBorderPainted(false);
        Controls.setBackground(new Color(0,0, 104));
        Controls.setForeground(Color.WHITE);
        Controls.addActionListener((actionEvent -> this.lf.setFrame("controls")));

        this.add(start);
        this.add(exit);
        this.add(Controls);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
