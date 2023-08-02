package warzone.menus;


import warzone.Launcher;
import warzone.constants.ResourcesConstants;
import warzone.loaders.ResourcesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;

            menuBackground = ResourcesManager.getSprite(ResourcesConstants.MENU_TITLE1);

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
