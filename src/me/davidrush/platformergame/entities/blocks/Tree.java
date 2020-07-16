package me.davidrush.platformergame.entities.blocks;

import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;

import java.awt.*;

public class Tree extends Block{
    public Tree(int x, int y, Level level) {
        super(x, y, Assets.tree, level);
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(super.getTexture(), (int)(super.getX() - super.getLevel().cameraX), (int)super.getY() - (super.getTexture().getHeight() - Block.height), null);
    }
}
