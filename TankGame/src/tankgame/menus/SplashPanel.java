package tankgame.menus;

import tankgame.Launcher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SplashPanel extends MenuPanel{
    public SplashPanel(Launcher lf) {
        super(lf, "splash.png");

        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            this.lf.setFrame("singIn");
        });
    }
}
