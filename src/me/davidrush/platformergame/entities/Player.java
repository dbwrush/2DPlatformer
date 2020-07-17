package me.davidrush.platformergame.entities;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.entities.blocks.Air;
import me.davidrush.platformergame.entities.blocks.Block;
import me.davidrush.platformergame.entities.blocks.Tallgrass;
import me.davidrush.platformergame.entities.blocks.Tree;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.level.Chunk;
import me.davidrush.platformergame.level.Level;
import me.davidrush.platformergame.states.GameState;

import java.awt.*;

public class Player extends Entity{

    private Game game;
    private GameState gameState;
    public static final int DEFAULT_HEALTH = 100, DEFAULT_JUMPCOUNT = 50;
    public static final float DEFAULT_ACCELERATION = 0.05f, DEFAULT_FRICTION = 0.2f;
    public static final float DEFAULT_GRAVITY = 0.1f;
    public static final int DEFAULT_WIDTH = 14,
                            DEFAULT_HEIGHT = 30;
    private Level level;
    protected int health, jumpCount;
    private long startTime;
    private String power;

    public Player(Game game, float x, float y, Level level, GameState gameState) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, level, DEFAULT_FRICTION, DEFAULT_ACCELERATION, game);
        this.game = game;
        this.level = level;
        health = DEFAULT_HEALTH;
        jumpCount = DEFAULT_JUMPCOUNT;
        acceleration = DEFAULT_ACCELERATION;
        xMove = 0;
        yMove = 0;
        drifting = true;
        power = "";
        this.gameState = gameState;
        startTime = System.nanoTime();
    }

    @Override
    public void tick() {
        getInput();
        move();
        checkEntityCollisions(x, y);
    }

    public void getInput() {
        xMove = 0;
        yMove = 0;
        drifting = true;
        yMomentum += DEFAULT_GRAVITY;
        boolean onFloor = checkLevelCollisions(x, y + 1);
        if(onFloor && jumpCount < DEFAULT_JUMPCOUNT) {
            jumpCount++;
        }
        if(game.getKeyManager().up && (onFloor || (power.equals("jetpack") && jumpCount > 0))) {//Player can only jump if they are on the ground.
            yMomentum = -acceleration * 60;
            if(!onFloor) {
                jumpCount--;
            }
        }
        if(game.getKeyManager().left) {
            drifting = false;
            if(onFloor) {//If they are on the ground, change velocity by acceleration, player is slower when moving left
                xMomentum -= acceleration;
            } else {
                xMomentum -= acceleration / 10;//If not on the ground, they can move left, but not as fast.
            }
        }
        if(game.getKeyManager().right) {
            drifting = false;
            if(onFloor) {//If they are on the ground, change velocity by +3 * acceleration
                xMomentum += acceleration * 3;
            } else {
                xMomentum += acceleration;//If not on the ground, they can move right, but not as fast.
            }
        }
        if(game.getKeyManager().stop) {
            xMomentum = 0f;
        }

        xMove = xMomentum;
        yMove = yMomentum;
    }

    @Override
    public void move() {
        super.move();
        level.cameraX = x + level.getCameraOffset();
        if(y > game.height) {//cannot go below floor!
            game.gameOver(gameState.getScore(), startTime);
        }
        /* Old Collision
        boolean xCollided = checkLevelCollisions(x + xMove, y);
        boolean yCollided = checkLevelCollisions(x, y + yMove);
        boolean bothCollided = checkLevelCollisions(x + xMove, y + yMove);
        //System.out.println("x: " + xCollided + ", y: " + yCollided + ", both: " + bothCollided);
        if(!bothCollided) {//both axes are fine
            x += xMove;
            level.cameraX += xMove;
            y += yMove;
        } else if(!yCollided) {//hit a wall, but can still go up or down
            y += yMove;
            xMomentum = 0;
        } else if(!xCollided) {//hit ceiling or floor. Can still walk left or right
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
            level.cameraX += xMove;
        } else {//Hit wall AND ceiling/floor, cannot move. Cancel momentum.
            xMomentum = 0;
            yMomentum = 0;
        }
        if(y < 0) {//cannot go above ceiling!
            y = 0;
            yMomentum = 0;
        }
        if(y > game.height) {//cannot go below floor!
            game.gameOver(gameState.getScore(), startTime);
        }*/
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, (int)(x - level.cameraX), (int) y, width, height, null);
    }

    public boolean checkEntityCollisions(float newX, float newY) {
        for(Enemy enemy : level.getEnemies()) {
            if(newX <= enemy.getX() + enemy.getWidth() &&
                    newX + width >= enemy.getX() &&
                    newY <= enemy.getY() + enemy.getHeight() &&
                    newY + height >= enemy.getY()) {
                if(yMove > 0 && newY < enemy.getY()) {
                    level.killEnemy(enemy);
                    yMomentum = - yMomentum;
                } else {
                    health -= 1;
                    //System.out.println("Health dropped to " + health);
                    xMomentum += enemy.getxMomentum() / 10;
                    if(health <= 0) {
                        game.gameOver(gameState.getScore(), startTime);
                    }
                }
                return true;
            }
        }
        for(PowerUp powerUp : level.getPowerUps()) {
            if(newX < powerUp.getX() + powerUp.getWidth() &&
                    newX + width >= powerUp.getX() &&
                    newY <= powerUp.getY() + powerUp.getHeight() &&
                    newY + height >= powerUp.getY()) {
                power = powerUp.getPower();
            }
        }
        if(Math.random() > 0.99 && health < DEFAULT_HEALTH) {
            health++;
        }
        return false;
    }

    //Getters and Setters
    public int getHealth() {
        return health;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String newPower) {
        power = newPower;
    }
}
