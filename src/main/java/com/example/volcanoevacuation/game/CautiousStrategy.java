package com.example.volcanoevacuation.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class CautiousStrategy implements MovementStrategy {
    private static final int[] DIR_X = {0, 1, 0, -1};
    private static final int[] DIR_Y = {1, 0, -1, 0};

    private static final int STOP_CHANCE_PERCENT = 20;
    private static final int TAKE_BEST_CHANCE_PERCENT = 60;

    private final Random random = new Random();

    @Override
    public Position chooseNextPosition(Map map, List<Agent> agents, Agent self, int[][] distance) {
        if (rollPercent(STOP_CHANCE_PERCENT)) {
            return null;
        }

        List<Position> candidates = collectValidMovesSortedByDistance(map, agents, self, distance);
        if (candidates.isEmpty()) return null;

        if (rollPercent(TAKE_BEST_CHANCE_PERCENT)) {
            return candidates.get(0);
        }

        return candidates.size() >= 2 ? candidates.get(1) : candidates.get(0);
    }

    private boolean rollPercent(int chancePercent) {
        return random.nextInt(100) < chancePercent;
    }

    private List<Position> collectValidMovesSortedByDistance(Map map, List<Agent> agents, Agent self, int[][] distance) {
        Position cur = self.getPosition();
        List<Position> candidates = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Position p = new Position(cur.x() + DIR_X[i], cur.y() + DIR_Y[i]);

            if (!isInsideMap(map, p)) continue;
            if (distance[p.y()][p.x()] == -1) continue;
            if (!map.isWalkable(p)) continue;
            if (isOccupied(agents, p, self)) continue;

            candidates.add(p);
        }

        candidates.sort((a, b) -> Integer.compare(distance[a.y()][a.x()], distance[b.y()][b.x()]));

        return candidates;
    }

    private boolean isInsideMap(Map map, Position p) {
        return p.x() >= 0 && p.x() < map.getWidth() && p.y() >= 0 && p.y() < map.getHeight();
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

