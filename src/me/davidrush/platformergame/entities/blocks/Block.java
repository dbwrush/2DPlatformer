package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.entities.Entity;
import me.davidrush.platformergame.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Block{
    public static int width = 32, height = 32;
    private float x, y;
    private BufferedImage texture;
    private Level level;
    public Block(float x, float y, BufferedImage texture, Level level) {
        this.level = level;
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(texture, (int)(x - level.cameraX), (int)y, null);
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public Level getLevel() {
        return level;
    }
}
