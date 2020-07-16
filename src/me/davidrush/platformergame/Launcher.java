package me.davidrush.platformergame;

import me.davidrush.display.Display;

public class Launcher {

    public static void main(String[] args) {
        Game game = new Game("2D Game", 1280, 720);
        game.start();
    }
}
