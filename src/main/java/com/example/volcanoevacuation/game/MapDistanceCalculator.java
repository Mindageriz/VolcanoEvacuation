package com.example.volcanoevacuation.game;

import java.util.ArrayDeque;
import java.util.List;
import java.util.function.Predicate;

public final class MapDistanceCalculator {

    private static final int UNVISITED = -1;
    private static final int[] DIR_X = {0, 1, 0, -1};
    private static final int[] DIR_Y = {1, 0, -1, 0};

    public int[][] calculate(Map map, List<Position> starts, Predicate<Position> canTraverse) {
        int[][] distances = createEmptyDistanceMap(map);

        if (starts == null || starts.isEmpty()) return distances;

        ArrayDeque<Position> queue = new ArrayDeque<>();
        for (Position p : starts) {
            distances[p.y()][p.x()] = 0;
            queue.add(p);
        }

        while (!queue.isEmpty()) {
            Position cur = queue.removeFirst();
            expandFrom(map, cur, distances, queue, canTraverse);
        }

        return distances;
    }

    private void expandFrom(
            Map map,
            Position cur,
            int[][] distances,
            ArrayDeque<Position> queue,
            Predicate<Position> canTraverse
    ) {
        int base = distances[cur.y()][cur.x()];

        for (int i = 0; i < 4; i++) {
            Position next = new Position(cur.x() + DIR_X[i], cur.y() + DIR_Y[i]);

            if (!isInsideMap(map, next)) continue;
            if (distances[next.y()][next.x()] != UNVISITED) continue;
            if (!canTraverse.test(next)) continue;

            distances[next.y()][next.x()] = base + 1;
            queue.add(next);
        }
    }

    private int[][] createEmptyDistanceMap(Map map) {
        int[][] d = new int[map.getHeight()][map.getWidth()];
        for (int y = 0; y < d.length; y++) {
            for (int x = 0; x < d[y].length; x++) {
                d[y][x] = UNVISITED;
            }
        }
        return d;
    }

    private boolean isInsideMap(Map map, Position p) {
        return p.x() >= 0 && p.x() < map.getWidth()
                && p.y() >= 0 && p.y() < map.getHeight();
    }
}

