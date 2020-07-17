package me.davidrush.platformergame;

import me.davidrush.display.Display;
import me.davidrush.platformergame.gfx.Assets;
import me.davidrush.platformergame.input.KeyManager;
import me.davidrush.platformergame.states.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;
    private String version = "2D Game Test v.1.4";

    private int highscore;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //States
    private State gameState;
    private State gameOverState;

    //Input
    private KeyManager keyManager;

    //File IO
    private FileManager fileManager;
    private ArrayList<String> storedData;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        fileManager = new FileManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        storedData = fileManager.readFile();
        parseStoredData();
        Assets.init();
        gameState = new GameState(this, highscore);
        State.setCurrentState(gameState);
    }

    private void tick() {
        keyManager.tick();
        if(State.getCurrentState() != null) {
            State.getCurrentState().tick();
        }
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.setFont(Assets.font);
        //clear screen
        g.clearRect(0,0, width, height);
        //draw here

        if(State.getCurrentState() != null) {
            State.getCurrentState().render(g);
        }
        g.drawString(version, width - 210, 20);

        //end drawing
        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        //game loop timer setup
        int desiredFps = 60;
        double timePerTick = 1000000000 / desiredFps;//timePerTick will be the number of nanoseconds per tick. 1 second is 1 billion nanoseconds.
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {

            //game loop timer
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println("fps: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public void gameOver(int newScore, long startTime) {
        long time = (System.nanoTime() - startTime) / 1000000000;
        if(newScore > highscore) {
            highscore = newScore;
            System.out.println("Writing new Highscore to file.");
            if(storedData.size() >= 1) {
                System.out.println("Overwriting existing highscore.");
                storedData.set(0, "highscore:" + highscore);
                fileManager.writeFile(storedData);
            } else {
                System.out.println("File is empty, adding highscore.");
                storedData.add("highscore:" + highscore);
                fileManager.writeFile(storedData);
            }
        }
        gameOverState = new GameOverState(this, newScore, highscore, (newScore / (double)time));
        State.setCurrentState(gameOverState);
    }

    public void startNewGame() {
        gameState = new GameState(this, highscore);
        State.setCurrentState(gameState);
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public synchronized void start() {
        if(!running) {
            running = true;
            thread = new Thread(this);
            thread.start();//calls the run method
        }
    }

    public synchronized void stop() {
        if(running) {
            try {
                thread.join();
            } catch(InterruptedException e) {

            }
        }
    }

    public void parseStoredData() {
        if(storedData.size() >= 1) {
            String string = storedData.get(0);
            if(string.startsWith("highscore:")) {
                String hsString = string.substring(10);
                int hs = Integer.parseInt(hsString);
                System.out.println("Found existing Highscore, " + hs);
                if(highscore < hs) {
                    highscore = hs;
                }
            } else {
                System.out.println("Found other data.");
                System.out.println(string);
            }
        }
    }

}
