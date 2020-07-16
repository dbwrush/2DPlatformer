package me.davidrush.platformergame.level;

import me.davidrush.platformergame.entities.blocks.*;

import java.awt.*;
import java.util.ArrayList;

public class Chunk {
    private static double airProp = 0.4, grassProp = 0.5, stoneProp = 0.7, stoneBricksProp = 0.8, stonePillarProp = 0.85;
    public static int width = 16, height = 16;
    public static int pixelHeight = height * Block.height;
    private boolean first;
    private int targetHeight;
    private int lastTargetHeight;
    private static int maxHeightChange = (int)(height * 0.33);
    int x, y;
    Level level;
    private Block[][] chunk = new Block[height][width];

    public Chunk(int x, int y, Level level, boolean first) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.first = first;
        generateChunk();
    }

    public void generateChunk() {
        //System.out.println("Generating chunk!");
        lastTargetHeight = height - 1;
        if(first) {//if first chunk generated
            lastTargetHeight = 8;
        } else {
            lastTargetHeight = level.getChunks().get(level.getChunks().size() - 1).getTargetHeight();
        }
        int heightChange = (int)(Math.random() * maxHeightChange * 2) - maxHeightChange;
        targetHeight = lastTargetHeight + heightChange;
        if(targetHeight > height - 1) {
            targetHeight = height -1;
        }
        if(targetHeight < 0) {
            targetHeight = 0;
        }

        double slope = ((double)(lastTargetHeight - targetHeight)) / width;
        double[] heights = new double[width];
        for(int i = 0; i < width; i++) {
            heights[i] = lastTargetHeight - (slope * i);
        }
        for(int row = 0; row < chunk.length; row++) {
            for(int col = 0; col < chunk[0].length; col++) {
                int x = this.x + (col * Block.width);
                int y = this.y + (row * Block.height);
                if(row > heights[col]) {
                    if(row - heights[col] < 1.1) {
                        chunk[row][col] = new Grass(x, y, level);
                    } else {
                        if (Math.random() * height > row - heights[col]) {
                            chunk[row][col] = new Dirt(x,y, level);
                        } else if(Math.random() > 0.9) {
                            chunk[row][col] = new StoneBricks(x,y, level);
                        } else if(Math.random() > 0.9) {
                            chunk[row][col] = new StonePillar(x,y, level);
                        } else {
                            chunk[row][col] = new Stone(x,y, level);
                        }
                    }
                } else {
                    if(heights[col] - row < 1 && Math.random() < 0.2 && row < height - 1) {
                        if(Math.random() < 0.3) {
                            chunk[row][col] = new Tree(x,y,level);
                        } else {
                            chunk[row][col] = new Tallgrass(x,y,level);
                        }
                    } else {
                        chunk[row][col] = new Air(x,y, level);
                    }
                }
            }
        }
    }

    public void tick() {
        /*for(int row = 0; row < chunk.length; row++) { //Currently no need to tick.
            for(int col = 0; col < chunk[0].length; col++) {
                chunk[row][col].tick();
            }
        }*/
    }

    public void render(Graphics g, float playerPos) {
        for(int row = 0; row < chunk.length; row++) {
            for(int col = 0; col < chunk[0].length; col++) {
                chunk[row][col].render(g);
            }
        }
    }

    public Block[][] getBlocks() {
        return chunk;
    }

    public int getTargetHeight() {
        return targetHeight;
    }
}
