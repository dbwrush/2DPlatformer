package me.davidrush.platformergame.entities;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.level.Level;

import java.awt.*;

public class PowerUp extends Entity{
    private static final float DEFAULT_ACCELERATION = 0.025f, DEFAULT_FRICTION = 0.025f;
    //public static ArrayList
    private String power;
    public PowerUp(float x, float y, int width, int height, Level level,  Game game) {
        super(x, y, width, height, level, DEFAULT_ACCELERATION, DEFAULT_ACCELERATION, game);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }
}
