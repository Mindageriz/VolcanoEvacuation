package com.example.volcanoevacuation.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch {
    public static int[][] executeMultiSourceBFS(Map map, List<Position> starts) {
        int height = map.getHeight();
        int width = map.getWidth();
        int[][] results = new int[height][width];


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                results[i][j] = -1;
            }
        }

        if (starts == null || starts.isEmpty()) {
            return results;
        }

        ArrayDeque<Position> deque = new ArrayDeque<>();
        for (Position start : starts) {
            results[start.y][start.x] = 0;
            deque.addLast(start);
        }


        int[] dirX = {0, 1, 0, -1, 1};
        int[] dirY = {1, 0, -1, 0, 1};

        while (!deque.isEmpty()) {
            Position current = deque.removeFirst();
            int curX = current.x;
            int curY = current.y;
            int curR = results[curY][curX];

            for (int i = 0; i < 4; i++) {
                int nextX = curX + dirX[i];
                int nextY = curY + dirY[i];

                if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
                    if (results[nextY][nextX] == -1) {
                        Position nextPosition = new Position(nextX, nextY);
                        if (map.isWalkable(nextPosition)) {
                            results[nextY][nextX] = curR + 1;
                            deque.addLast(nextPosition);
                        }
                    }
                }
            }
        }
        return results;
    }

    public static int[][] executeLavaMultiSourceBFS(Map map, List<Position> starts) {
        int height = map.getHeight();
        int width = map.getWidth();
        int[][] results = new int[height][width];


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                results[i][j] = -1;
            }
        }

        if (starts == null || starts.isEmpty()) {
            return results;
        }

        ArrayDeque<Position> deque = new ArrayDeque<>();
        for (Position start : starts) {
            results[start.y][start.x] = 0;
            deque.addLast(start);
        }


        int[] dirX = {0, 1, 0, -1, 1};
        int[] dirY = {1, 0, -1, 0, 1};

        while (!deque.isEmpty()) {
            Position current = deque.removeFirst();
            int curX = current.x;
            int curY = current.y;
            int curR = results[curY][curX];

            for (int i = 0; i < 4; i++) {
                int nextX = curX + dirX[i];
                int nextY = curY + dirY[i];

                if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
                    if (results[nextY][nextX] == -1) {
                        Position nextPosition = new Position(nextX, nextY);
                        Tile tile = map.getTile(nextPosition);
                        if (tile != Tile.BARRICADE && tile != Tile.BORDER && tile != Tile.SAFE_ZONE) {
                            results[nextY][nextX] = curR + 1;
                            deque.addLast(nextPosition);
                        }
                    }
                }
            }
        }
        return results;
    }

}