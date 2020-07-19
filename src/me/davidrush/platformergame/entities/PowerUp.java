package me.davidrush.platformergame.entities;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PowerUp extends Entity{
    private static final float DEFAULT_ACCELERATION = 0.025f, DEFAULT_FRICTION = 0.025f, DEFAULT_GRAVITY = 0.1f;
    private static String[] powerUps = {"jetpack"};
    private String power;
    private BufferedImage sprite;
    public PowerUp(float x, float y, Level level,  Game game) {
        super(x, y, 16, 16, level, DEFAULT_ACCELERATION, DEFAULT_ACCELERATION, game);
        power = powerUps[(int)(Math.random() * powerUps.length)];
        if(power.equals("jetpack")) {
            sprite = Assets.jetpack;
        }
        if(Math.random() > 0.5) {
            xMomentum = 1;
        } else {
            xMomentum = -1;
        }
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;
        yMomentum += DEFAULT_GRAVITY;
        xMove = xMomentum;
        yMove = yMomentum;
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, (int)(x - level.cameraX), (int) y, width, height, null);
    }

    public String getPower() {
        return power;
    }
}
