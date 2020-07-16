package me.davidrush.platformergame.entities;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.entities.blocks.Air;
import me.davidrush.platformergame.entities.blocks.Block;
import me.davidrush.platformergame.entities.blocks.Tallgrass;
import me.davidrush.platformergame.entities.blocks.Tree;
import me.davidrush.platformergame.level.Chunk;
import me.davidrush.platformergame.level.Level;

import java.awt.*;

public abstract class Entity {
    protected float x, y;
    protected float xMomentum, yMomentum;
    protected float xMove, yMove;
    protected int width, height;
    protected float friction;
    protected float acceleration;
    protected boolean drifting;
    protected Level level;
    protected Game game;

    public Entity(float x, float y, int width, int height, Level level, float friction, float acceleration, Game game) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.level = level;
        this.friction = friction;
        this.acceleration = acceleration;
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public boolean checkLevelCollisions(float newX, float newY) {
        for(Chunk chunk : level.getChunks()) {
            for(Block[] row : chunk.getBlocks()) {
                for(Block b : row) {
                    if(newX <= b.getX() + b.getWidth() &&
                            newX + width >= b.getX() &&
                            newY <= b.getY() + b.getHeight() &&
                            newY + height >= b.getY() && (b instanceof Air == false) && (b instanceof Tallgrass == false) && (b instanceof Tree == false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void move() {
        boolean xCollided = checkLevelCollisions(x + xMove, y);
        boolean yCollided = checkLevelCollisions(x, y + yMove);
        boolean bothCollided = checkLevelCollisions(x + xMove, y + yMove);
        if(!bothCollided) {//both axes are fine
            x += xMove;
            y += yMove;
        } else if(!yCollided) {//hit a wall, but can still go up or down
            y += yMove;
            xMove = 0;
            xMomentum = (float)(xMomentum * 0.5);
        } else if(!xCollided) {//hit ceiling or floor. Can still walk left or right
            yMomentum = 0;
            yMove = 0;
            if(drifting) {//If drifting, APPLY FRICTION!
                if (xMomentum > friction) {//xMomentum decreases linearly until reaching 0.
                    xMomentum -= friction;
                } else if(xMomentum < -friction) {
                    xMomentum += friction;
                } else {
                    xMomentum = 0;
                }
            }
            x += xMove;
        } else {//Hit wall AND ceiling/floor, cannot move. Cancel momentum.
            xMomentum = (float)(xMomentum * 0.5);
            yMomentum = (float)(yMomentum * 0.5);
            xMove = 0;
            yMove = 0;
        }
        if(y < 0) {//cannot go above ceiling!
            y = 0;
            yMomentum = 0;
        }
        /* Collision Interpretation pre 7/15/2020
        boolean xCollided = checkLevelCollisions(x + xMove, y);
        boolean yCollided = checkLevelCollisions(x, y + yMove);
        boolean bothCollided = checkLevelCollisions(x + xMove, y + yMove);
        if(!bothCollided) {//both clear
            x += xMove;
            y += yMove;
            return;
        }
        if(!yCollided && xCollided) {//only Y axis is clear
            y += yMove;
            xMove = 0;
            xMomentum = (float)(xMomentum * 0.5);
            return;
        }
        if(!xCollided && yCollided) {//only X axis is clear
            x += xMove;
            yMove = 0;
            yMomentum = (float)(yMomentum * 0.5);
            if(drifting) {
                if (xMomentum > friction) {//xMomentum decreases linearly until reaching 0.
                    xMomentum -= friction;
                } else if(xMomentum < -friction) {
                    xMomentum += friction;
                } else {
                    xMomentum = 0;
                }
            }
            return;
        }
        if(bothCollided && yCollided && xCollided) {//wow, everything leads to a collision.
            xMomentum = (float)(xMomentum * 0.5);
            yMomentum = (float)(yMomentum * 0.5);
            xMove = 0;
            yMove = 0;
        }*/
        //Old Collision Interpretation
        /*if(xCollided && yCollided) {//Hit wall AND ceiling/floor, cannot move. Cancel momentum.
            xMomentum = 0;
            yMomentum = 0;
        }
        if(!bothCollided) {//both axes are fine
            x += xMove;
            y += yMove;
        }
        if(xCollided && !yCollided) {//hit a wall, but can still go up or down
            y += yMove;
            xMomentum = 0;
        }
        if(!xCollided && yCollided) {//hit ceiling or floor. Can still walk left or right
            yMomentum = 0;
            if(drifting) {//If drifting, APPLY FRICTION!
                if (xMomentum > friction) {//xMomentum decreases linearly until reaching 0.
                    xMomentum -= friction;
                } else if(xMomentum < -friction) {
                    xMomentum += friction;
                } else {
                    xMomentum = 0;
                }
            }
            x += xMove;
        }*/
    }

    public float getxMomentum() {
        return xMomentum;
    }

    public float getyMomentum() {
        return yMomentum;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
