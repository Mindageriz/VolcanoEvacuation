package com.example.volcanoevacuation.game;

import java.util.List;

public final class ShortestPathStrategy implements MovementStrategy {
    private static final int INF_DISTANCE = 9999;
    private static final int[] DIR_X = {0, 1, 0, -1};
    private static final int[] DIR_Y = {1, 0, -1, 0};

    @Override
    public Position chooseNextPosition(Map map, List<Agent> agents, Agent self, int[][] distance) {
        Position cur = self.getPosition();

        int bestDist = INF_DISTANCE;
        Position bestPos = null;

        for (int i = 0; i < 4; i++) {
            Position candidate = new Position(cur.x() + DIR_X[i], cur.y() + DIR_Y[i]);

            if (!isInsideMap(map, candidate)) continue;

            int d = distance[candidate.y()][candidate.x()];
            if (d == -1) continue;

            if (!map.isWalkable(candidate)) continue;
            if (isOccupied(agents, candidate, self)) continue;

            if (d < bestDist) {
                bestDist = d;
                bestPos = candidate;
            }
        }

        return bestPos;
    }

    private boolean isInsideMap(Map map, Position p) {
        return p.x() >= 0 && p.x() < map.getWidth()
                && p.y() >= 0 && p.y() < map.getHeight();
    }

    private boolean isOccupied(List<Agent> agents, Position pos, Agent self) {
        for (Agent a : agents) {
            if (a == self) continue;
            if (!a.isInDanger()) continue;

            Position p = a.getPosition();
            if (p.x() == pos.x() && p.y() == pos.y()) return true;
        }
        return false;
    }
}

