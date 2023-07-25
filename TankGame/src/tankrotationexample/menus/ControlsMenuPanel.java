package tankrotationexample.menus;


import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourcesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControlsMenuPanel extends JPanel {

    private BufferedImage background;
    private final Launcher lf;

    public ControlsMenuPanel(Launcher lf){
        this.lf = lf;
        background = ResourcesManager.getSprite("controls");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton back = new JButton("Back");
        back.setFont(new Font("Courier New",Font.BOLD,24));
        back.setBounds(140,400,150,50);
        back.addActionListener(actionEvent -> this.lf.setFrame("start"));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setBackground(new Color(0, 0, 253));
        back.setForeground(Color.WHITE);

        this.add(back);


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.background, 0, 0, null);
    }

}
