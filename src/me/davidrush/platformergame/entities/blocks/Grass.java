package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

public class Grass extends Block{
    public Grass(int x, int y, Level level) {
        super(x, y, Assets.grass, level);
    }
}
