package com.example.volcanoevacuation.game;

public enum Tile {
    ROAD('.'),
    HOUSE('H'),
    SAFE_ZONE('S'),
    BARRICADE('#'),
    VOLCANO('V'),
    LAVA('~'),
    BORDER('@');

    public final char symbol;
    private Tile(char symbol) {
        this.symbol = symbol;
    }

    public static Tile get(char symbol) {
        for (Tile t : Tile.values()) {
            if (t.symbol == symbol) {
                return t;
            }
        }
        return ROAD;
    }
}
