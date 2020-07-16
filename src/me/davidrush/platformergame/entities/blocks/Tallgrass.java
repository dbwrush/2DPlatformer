package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

import java.awt.image.BufferedImage;

public class Tallgrass extends Block{
    public Tallgrass(float x, float y, Level level) {
        super(x, y, Assets.tallgrass, level);
    }
}
