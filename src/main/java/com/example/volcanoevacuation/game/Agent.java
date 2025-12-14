package com.example.volcanoevacuation.game;

public final class Agent {
    private Position position;
    private boolean alive = true;
    private boolean safe = false;
    private final MovementStrategy strategy;

    public Agent(Position position, MovementStrategy strategy) {
        this.position = position;
        this.strategy = strategy;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isSafe() {
        return safe;
    }

    public boolean isInDanger() {
        return alive && !safe;
    }

    public void moveTo(Position newPosition) {
        this.position = newPosition;
    }

    public void markSafe() {
        if (!alive) return;
        safe = true;
    }

    public void kill() {
        alive = false;
        safe = false;
    }

    public MovementStrategy getStrategy() {
        return strategy;
    }
}


