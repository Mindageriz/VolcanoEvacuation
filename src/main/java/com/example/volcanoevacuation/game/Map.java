package com.example.volcanoevacuation.game;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final Tile[][] tiles;

    public Map(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(Position position) {
        return tiles[position.y()][position.x()];
    }

    public void setTile(Position position, Tile tile) {
        tiles[position.y()][position.x()] = tile;
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public List<Position> getAllTilePositions(Tile target) {
        List<Position> positions = new ArrayList<>();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == target) {
                    positions.add(new Position(x, y));
                }
            }
        }
        return positions;
    }

    public boolean isWalkable(Position position) {
        Tile tile = getTile(position);
        return tile == Tile.ROAD || tile == Tile.SAFE_ZONE;
    }
}





