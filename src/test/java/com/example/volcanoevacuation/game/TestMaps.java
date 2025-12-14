package com.example.volcanoevacuation.game;

final class TestMaps {
    private TestMaps() {}

    static Map mapFrom(String... rows) {
        Tile[][] tiles = new Tile[rows.length][rows[0].length()];
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[y].length(); x++) {
                tiles[y][x] = Tile.get(rows[y].charAt(x));
            }
        }
        return new Map(tiles);
    }
}
