package com.example.volcanoevacuation.game;

import java.util.List;

public final class AgentMovementService {

    private final MapDistanceCalculator distanceCalculator = new MapDistanceCalculator();

    public void moveAgents(Map map, List<Agent> agents) {
        int[][] distanceToSafe = computeDistanceToSafeZones(map);

        for (Agent a : agents) {
            if (!a.isInDanger()) continue;

            if (map.getTile(a.getPosition()) == Tile.SAFE_ZONE) {
                a.markSafe();
                continue;
            }

            Position next = a.getStrategy().chooseNextPosition(map, agents, a, distanceToSafe);
            if (next != null) {
                a.moveTo(next);
            }
        }
    }

    private int[][] computeDistanceToSafeZones(Map map) {
        List<Position> safeZones = map.getAllTilePositions(Tile.SAFE_ZONE);
        return distanceCalculator.calculate(map, safeZones, map::isWalkable);
    }
}



