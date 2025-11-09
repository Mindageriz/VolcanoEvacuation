package com.example.volcanoevacuation.game;

public class Agent {
    private Position position;
    private boolean alive = true;
    private boolean safe = false;

    public Agent(Position position) { this.position = position; }

    public boolean isAlive() { return alive; }
    public boolean isSafe() { return safe; }
    public Position getPosition() { return position; }

    public void setDead() { alive = false; }
    public void setSafe() { safe = true; }

    public void moveTo(Position position) { this.position = position; }
}
