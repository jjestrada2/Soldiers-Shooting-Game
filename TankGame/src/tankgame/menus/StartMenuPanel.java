package tankgame.menus;


import tankgame.Launcher;
import tankgame.constants.GameScreenConstants;


import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends MenuPanel {
    private JButton start;
    private JButton maps;
    private JButton help;
    private JButton exit;

    public StartMenuPanel(Launcher lf) {
        super(lf, "title.png");
        start = super.createButton("START", 24, 175, 320, 175, 40, "game");
        maps = super.createButton("MAPS", 24, 175, 370, 175, 40, "maps");
        exit = new JButton("EXIT");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175,420,175,40);
        exit.setBackground(Color.decode(GameScreenConstants.BUTTON_BACKGROUND_COLOR));
        exit.setForeground(Color.decode(GameScreenConstants.BUTTON_FOREGROUND_COLOR));
        exit.setFocusPainted(false);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(maps);
        this.add(exit);
    }
}
