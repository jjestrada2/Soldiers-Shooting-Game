package warzone.menus;

import warzone.Launcher;
import warzone.loaders.ResourcesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SplashPanel extends JPanel {
    private BufferedImage menuBackground;
    private final Launcher lf;

    public SplashPanel(Launcher lf) {
        this.lf = lf;

        menuBackground = ResourcesManager.getSprite("splash");

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            this.lf.setFrame("singIn");
        });






    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}

