package com.example.volcanoevacuation.game;

import java.util.List;
import java.util.Random;

public final class AgentSpawner {
    private static final int[] DX_8 = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int[] DY_8 = {-1, -1, -1, 0, 0, 1, 1, 1};

    private static final int CAUTIOUS_PERCENT = 20;

    private final Random random = new Random();

    public void spawnAroundHouses(Map map, List<Agent> agents) {
        List<Position> houses = map.getAllTilePositions(Tile.HOUSE);
        for (Position house : houses) {
            spawnAround(map, agents, house);
        }
    }

    private void spawnAround(Map map, List<Agent> agents, Position center) {
        for (int i = 0; i < DX_8.length; i++) {
            Position candidate = new Position(center.x() + DX_8[i], center.y() + DY_8[i]);

            if (map.isWalkable(candidate)) {
                MovementStrategy strategy = pickStrategy();
                agents.add(new Agent(candidate, strategy));
            }
        }
    }

    private MovementStrategy pickStrategy() {
        if (random.nextInt(100) < CAUTIOUS_PERCENT) {
            return new CautiousStrategy();
        }
        return new ShortestPathStrategy();
    }
}



