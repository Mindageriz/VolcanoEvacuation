package com.example.volcanoevacuation.agent;

import com.example.volcanoevacuation.game.Map;
import com.example.volcanoevacuation.game.Position;
import com.example.volcanoevacuation.game.Tile;

import java.util.List;

public final class AgentSpawner {

    private static final int[] DX_8 = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int[] DY_8 = {-1, -1, -1, 0, 0, 1, 1, 1};

    private final AgentFactory agentFactory = new AgentFactory();

    public void spawnAroundHouses(Map map, List<Agent> agents) {
        List<Position> houses = map.getAllTilePositions(Tile.HOUSE);

        for (Position house : houses) {
            spawnAround(map, agents, house);
        }
    }

    private void spawnAround(Map map, List<Agent> agents, Position center) {
        for (int i = 0; i < DX_8.length; i++) {
            Position candidate = new Position(
                    center.x() + DX_8[i],
                    center.y() + DY_8[i]
            );

            if (map.isWalkable(candidate)) {
                agents.add(agentFactory.createAgent(candidate));
            }
        }
    }
}




