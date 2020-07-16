package me.davidrush.platformergame.states;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.input.KeyManager;

import java.awt.*;

public class GameOverState extends State{
    private int score;
    private int highscore;
    private double avgSpeed;
    private KeyManager keyManager;
    public GameOverState(Game game, int score, int highscore, double avgSpeed) {
        super(game);
        keyManager = game.getKeyManager();
        this.score = score;
        this.highscore = highscore;
        this.avgSpeed = avgSpeed;
    }

    @Override
    public void tick() {
        if(keyManager.up) {
            game.startNewGame();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawString("GAME OVER", 100, 100);
        g.drawString("Score: " + score + " blocks", 100, 130);
        g.drawString("Highscore: " + highscore + " blocks", 100, 150);
        g.drawString("Average Speed: " + avgSpeed + " blocks per second", 100, 190);
        g.drawString("PRESS W TO TRY AGAIN", 100, 220);
    }
}
