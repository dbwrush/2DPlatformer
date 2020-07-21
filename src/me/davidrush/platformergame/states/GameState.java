package me.davidrush.platformergame.states;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.entities.Player;
import me.davidrush.platformergame.entities.blocks.Block;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Chunk;
import me.davidrush.platformergame.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameState extends State{
    private BufferedImage currentPowerUp = Assets.player;
    private Player player;
    private Level level;
    public float cameraX;
    private int score;
    private int highscore;
    private boolean drawPowerUp = false;

    public GameState(Game game, int highscore) {
        super(game);
        level = new Level(game, this);
        player = level.getPlayer();
        this.highscore = highscore;
    }

    @Override
    public void tick() {
        level.tick();
        if(player.getX() / Block.width > score) {
            score = (int)player.getX() / Block.width;
        }
    }

    @Override
    public void render(Graphics g) {
        level.render(g);
        g.drawString("Score: " + Integer.toString(score), 10, 20);
        if(score > highscore) {
            g.drawString("Highscore: " + Integer.toString(score), 10, 50);
        } else {
            g.drawString("Highscore: " + Integer.toString(highscore), 10, 50);
        }
        g.drawString("Health: " + Integer.toString(player.getHealth()), 10, 80);
        g.drawString("Energy: " + Integer.toString(player.getEnergy()), 10, 110);
        g.drawString("Speed: " + Integer.toString(Math.abs(Math.round(player.getxMomentum()))), 10, 140);
        if(drawPowerUp) {
            g.drawImage(currentPowerUp, 10, game.height - currentPowerUp.getHeight() * 2 - 10, currentPowerUp.getWidth() * 2, currentPowerUp.getHeight() * 2, null);
            g.drawString(Integer.toString((int)(player.getPowerUpRemaining() / 60.0)), currentPowerUp.getWidth() + 20, game.height - currentPowerUp.getHeight() * 2 - 10);
        }
    }

    public int getScore() {
        return score;
    }

    public void setCurrentPowerUp(String powerName) {
        drawPowerUp = true;
        if(powerName.equals("jetpack")) {
            currentPowerUp = Assets.jetpack;
        } else if(powerName.equals("speed")) {
            currentPowerUp = Assets.speed;
        } else if(powerName.equals("shield")) {
            currentPowerUp = Assets.shield;
        } else if(powerName.equals("invincibility")) {
            currentPowerUp = Assets.invincibility;
        } else {
            currentPowerUp = Assets.player;
            drawPowerUp = false;
        }
    }
}
