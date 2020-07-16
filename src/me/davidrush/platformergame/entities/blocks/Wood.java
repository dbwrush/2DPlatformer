package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

public class Wood extends Block{
    public Wood(int x, int y, Level level) {
        super(x, y, Assets.wood, level);
    }
}

