package me.davidrush.platformergame.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, grass, stone, stoneBricks, stonePillar, wood, dirt, tallgrass, tree, enemy, bg1, bg2, jetpack, shield, speed, invincibility;
    public static Font font;
    public static void init() {
        player = ImageLoader.loadImage("/textures/player.png");
        grass = ImageLoader.loadImage("/textures/grass.png");
        stone = ImageLoader.loadImage("/textures/stone.png");
        stoneBricks = ImageLoader.loadImage("/textures/stoneBricks.png");
        stonePillar = ImageLoader.loadImage("/textures/stonePillar.png");
        wood = ImageLoader.loadImage("/textures/wood.png");
        dirt = ImageLoader.loadImage("/textures/dirt.png");
        tallgrass = ImageLoader.loadImage("/textures/tallgrass.png");
        tree = ImageLoader.loadImage("/textures/tree.png");
        enemy = ImageLoader.loadImage("/textures/enemy.png");
        bg1 = ImageLoader.loadImage("/textures/bg1.png");
        bg2 = ImageLoader.loadImage("/textures/bg2.png");
        jetpack = ImageLoader.loadImage("/textures/jetpack.png");
        shield = ImageLoader.loadImage("/textures/shield.png");
        speed = ImageLoader.loadImage("/textures/speed.png");
        invincibility = ImageLoader.loadImage("/textures/invincibility.png");
        font = new Font("Ariel", Font.BOLD, 20);
        System.out.println("Assets loaded");
    }
}
