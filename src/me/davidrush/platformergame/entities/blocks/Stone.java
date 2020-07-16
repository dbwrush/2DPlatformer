package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

public class Stone extends Block{
    public Stone(int x, int y, Level level) {
        super(x, y, Assets.stone, level);
    }
}
