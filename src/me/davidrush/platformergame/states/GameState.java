package me.davidrush.platformergame.states;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.entities.Player;
import me.davidrush.platformergame.entities.blocks.Block;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Chunk;
import me.davidrush.platformergame.level.Level;

import java.awt.*;

public class GameState extends State{

    private Player player;
    private Level level;
    public float cameraX;
    private int score;
    private int highscore;

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
        g.drawString("Fuel: " + Integer.toString(player.getJumpCount()), 10, 110);
        g.drawString("Speed: " + Integer.toString(Math.round(player.getxMomentum())), 10, 140);
    }

    public int getScore() {
        return score;
    }
}
