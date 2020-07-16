package me.davidrush.platformergame.states;

import me.davidrush.platformergame.Game;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    public static void setCurrentState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {
        return currentState;
    }

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

}
