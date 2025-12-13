package com.example.volcanoevacuation.game;

public abstract class MapLoader {
    public final Map load() {
        String[] raw = rawMap();
        return parse(raw);
    }

    protected abstract String[] rawMap();

    private Map parse(String[] raw) {
        Tile[][] tiles = new Tile[raw.length][raw[0].length()];
        for (int y = 0; y < raw.length; y++) {
            for (int x = 0; x < raw[y].length(); x++) {
                tiles[y][x] = Tile.get(raw[y].charAt(x));
            }
        }
        return new Map(tiles);
    }
}

