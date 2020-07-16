package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

public class StonePillar extends Block{
    public StonePillar(int x, int y, Level level) {
        super(x, y, Assets.stonePillar, level);
    }
}
