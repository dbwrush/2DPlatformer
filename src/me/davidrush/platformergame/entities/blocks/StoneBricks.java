package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

public class StoneBricks extends Block{
    public StoneBricks(int x, int y, Level level) {
        super(x, y, Assets.stoneBricks, level);
    }
}
