package com.example.volcanoevacuation.game;

import java.util.*;

public class Map {
    private Tile[][] tiles;

    public Map(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(Position position) {
        return tiles[position.y][position.x];
    }

    public void setTile(Position position, Tile tile) {
        this.tiles[position.y][position.x] = tile;
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public List<Position> getAllTilePositions(Tile target) {
        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == target) {
                    // x = col y = row -> tiles[y][x] duuuuh
                    positions.add(new Position(j, i));
                }
            }
        }
        return positions;
    }

    public boolean isWalkable(Position position) {
        Tile tile = getTile(position);
        return tile == Tile.ROAD || tile == Tile.SAFE_ZONE;
    }

    public static Map loadMap() {
        String[] raw = {
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "@............#..............#............@",
                "@..H.........#........V.....#........H...@",
                "@............#..............#............@",
                "@######......#########......#............@",
                "@...........................#............@",
                "@#################.......................@",
                "@................#.......####............@",
                "@.....S.....................#############@",
                "@.......................................S@",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        };
        Tile[][] tiles = new Tile[raw.length][raw[0].length()];
        for (int i = 0; i < raw.length; i++) {
            for (int j = 0; j < raw[i].length(); j++) {
                tiles[i][j] = Tile.get(raw[i].charAt(j));
            }
        }
        return new Map(tiles);
    }

    public void printMap(Map map, List<Agent> agents, int panic, int safe, int dead, int toBreak, int toPlace) {
        System.out.println("\n".repeat(50));

        int height = tiles.length;
        int width  = tiles[0].length;

        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            if (x == width - 1) {
                System.out.print(" ");
            } else {
                int tens = x / 10;
                if (tens == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(tens);
                }
            }
        }
        System.out.println();
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            if (x == 0 || x == width - 1) {
                System.out.print(" ");
            } else {
                System.out.print((x % 10));
            }
        }
        System.out.println();

        for (int y = 0; y < tiles.length; y++) {

            if (y == 0 || y == height - 1) { System.out.print("    "); }
            else { System.out.printf("%3d ", y);}

            for (int x = 0; x < tiles[y].length; x++) {
                char ch = tiles[y][x].symbol;

                if (ch != Tile.SAFE_ZONE.symbol) {
                    for (Agent a : agents) {
                        Position p = a.getPosition();
                        if (p.x == x && p.y == y) {
                            if (!a.isAlive()) {
                                ch = '+';
                            } else if (!a.isSafe()) {
                                ch = '*';
                            }
                            break;
                        }
                    }
                }

                System.out.print(ch);
            }
            if (y == 0) {
                System.out.print("          AGENTS: *");
            }
            if (y == 1) {
                System.out.print("          in panic: " + panic);
            }
            if (y == 2) {
                System.out.print("              safe: " + safe);
            }
            if (y == 3) {
                System.out.print("              dead: " + dead);
            }
            if (y == 5) {
                System.out.print("      BARRICADES: #");
            }
            if (y == 6) {
                System.out.print("          to break: " + toBreak);
            }
            if (y == 7) {
                System.out.print("          to place: " +  toPlace);
            }

            System.out.print("\n");
        }
    }
}




