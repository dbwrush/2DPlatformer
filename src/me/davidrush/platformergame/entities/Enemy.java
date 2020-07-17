package me.davidrush.platformergame.entities;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Level;
import me.davidrush.platformergame.states.GameState;

import java.awt.*;

public class Enemy extends Entity{
    protected static final int DEFAULT_WIDTH = 14, DEFAULT_HEIGHT = 30;
    protected static final float DEFAULT_FRICTION = 0.2f, DEFAULT_ACCELERATION = 0.10f, DEFAULT_GRAVITY = 0.1f;

    public Enemy(Game game, float x, float y, Level level) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, level, DEFAULT_FRICTION, DEFAULT_ACCELERATION, game);
    }

    public void planMovment() {
        xMove = 0;
        yMove = 0;
        int distanceToPlayer = Math.round(level.getPlayer().x - x);
        boolean onFloor = checkLevelCollisions(x, y + 1);
        yMomentum += DEFAULT_GRAVITY;
        if(distanceToPlayer < 0) {//if enemy is ahead of player, move left.
            drifting = false;
            if(onFloor) {//If they are on the ground, change velocity by acceleration
                xMomentum -= acceleration;
            } else {
                xMomentum -= acceleration / 10;//If not on the ground, they can move left, but not as fast.
            }
        } else if (distanceToPlayer > 0){//if behind player, move right
            drifting = false;
            if(onFloor) {//If they are on the ground, change velocity by +3 * acceleration
                xMomentum += acceleration;
            } else {
                xMomentum += acceleration / 10;//If not on the ground, they can move right, but not as fast.
            }
        }
        if(checkLevelCollisions(x + xMomentum, y) && onFloor) {
            yMomentum = -acceleration * 30;
        }
        xMove = xMomentum;
        yMove = yMomentum;
    }

    @Override
    public void tick() {
        planMovment();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.enemy, (int)(x - level.cameraX), (int) y, width, height, null);
    }
}
