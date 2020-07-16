package me.davidrush.platformergame.entities.background;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;
import me.davidrush.platformergame.states.GameState;

import java.awt.*;

public class Background {
    private Game game;
    private Level level;
    private GameState gameState;
    private float x;
    private static int width = 5760, height = 720;
    private static double moveRate = 0.7;
    public Background(Game game, float x, Level level, GameState gameState) {
        this.game = game;
        this.x = x;
        this.level = level;
        this.gameState = gameState;
    }
    public void tick(double moveDistance) {
        x +=moveDistance;
    }
    public void render(Graphics g) {
        g.drawImage(Assets.bg2, (int)(x - level.cameraX), 0, width, height, null);
    }
    public static double getMoveRate() {
        return moveRate;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public int getX() {
        return (int)x;
    }
}
