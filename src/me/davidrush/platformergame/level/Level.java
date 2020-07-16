package me.davidrush.platformergame.level;

import me.davidrush.platformergame.Game;
import me.davidrush.platformergame.entities.Enemy;
import me.davidrush.platformergame.entities.Player;
import me.davidrush.platformergame.entities.background.Background;
import me.davidrush.platformergame.entities.background.Midground;
import me.davidrush.platformergame.entities.blocks.Block;
import me.davidrush.platformergame.entities.blocks.Grass;
import me.davidrush.platformergame.states.GameState;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private int frameNum = 0;
    private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
    private ArrayList<Midground> midgrounds = new ArrayList<Midground>();
    private ArrayList<Background> backgrounds = new ArrayList<Background>();
    private Game game;
    private Player player;
    private int chunksLength = 0, cameraOffset;
    public float cameraX;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private GameState gameState;
    public Level(Game game, GameState gameState) {
        player = new Player(game, 0, 0, this, gameState);
        this.game = game;
        this.gameState = gameState;
        cameraOffset = -game.width / 3;
        cameraX = player.getX() + cameraOffset;
        int neededChunks = (int)Math.ceil(game.width / (Chunk.width * Block.width)) * 2;
        Chunk newChunk = new Chunk(chunksLength, game.height - Chunk.pixelHeight, this, true);
        chunks.add(newChunk);
        chunksLength += Chunk.width * Block.width;
        for(int i = 1; i < neededChunks; i++) {
            newChunk = new Chunk(chunksLength, game.height - Chunk.pixelHeight, this, false);
            chunks.add(newChunk);
            chunksLength += Chunk.width * Block.width;
        }
        midgrounds.add(new Midground(game, cameraX, this, gameState));
        backgrounds.add(new Background(game, cameraX, this, gameState));
    }
    public void tick() {
        float startCameraX = cameraX;
        player.tick();
        if(chunksLength - cameraX < game.width) {
            //System.out.println("Adding new chunk, culling first");
            Chunk newChunk = new Chunk(chunksLength, game.height - Chunk.pixelHeight, this, false);
            chunks.add(newChunk);
            chunksLength += Chunk.width * Block.width;
            if(Math.random() > 0.6) {
                double offset = (Math.random() * Chunk.width * Block.width);
                //System.out.println("Offset" + offset);
                Enemy newEnemy = new Enemy(game, chunksLength - (int)offset, 0, this);
                enemies.add(newEnemy);
            }
            chunks.remove(0);
            if(enemies.size() > 10) {
                enemies.remove(0);
            }
        }
        /*for(Chunk chunk : chunks) { //Currently no need to tick chunks, so don't bother.
            chunk.tick();
        }*/
        for(Enemy enemy : enemies) {
            enemy.tick();
        }
        int midEnd = midgrounds.get(midgrounds.size() - 1).getX() + Midground.getWidth();
        int backEnd = backgrounds.get(backgrounds.size() - 1).getX() + Background.getWidth();
        if(midEnd - cameraX < game.width) {
            //System.out.println("Adding new Midground!");
            midgrounds.add(new Midground(game, midEnd, this, gameState));
            if(midgrounds.size() > 2) {
                midgrounds.remove(0);
            }
        }
        if(backEnd - cameraX < game.width) {
            backgrounds.add(new Background(game, backEnd, this, gameState));
            if(backgrounds.size() > 2) {
                backgrounds.remove(0);
            }
        }
        float distance = cameraX - startCameraX;
        for(Midground midground: midgrounds) {
            midground.tick(distance * Midground.getMoveRate());
        }
        for(Background background : backgrounds) {
            background.tick(distance * Background.getMoveRate());
        }
    }
    public void render(Graphics g) {
        for(Background background : backgrounds) {
            background.render(g);
        }
        for(Midground midground : midgrounds) {
            midground.render(g);
        }
        for(Chunk chunk : chunks) {
            chunk.render(g, player.getX());
        }
        for(Enemy enemy : enemies) {
            enemy.render(g);
        }
        player.render(g);
    }

    public void killEnemy(Enemy e) {
        enemies.remove(e);
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getCameraOffset() {
        return cameraOffset;
    }
}
/*
todo:
 Game-Over:
    a. Player takes damage if they hit a wall too fast.
    b. Display HP and x on the screen. Maybe also time?
    c. Create a Game-Over screen. Here, they see big text that says "GAME OVER", as well as their sore. If they press ENTER, a new game will start.
 Saving:
    High-scores are saved to a file so that if they quit and come back they will be able to see their best score yet.
 */