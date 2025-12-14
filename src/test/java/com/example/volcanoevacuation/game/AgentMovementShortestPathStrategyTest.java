package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.services.AgentMovementService;
import com.example.volcanoevacuation.strategies.MapDistanceCalculator;
import com.example.volcanoevacuation.strategies.ShortestPathStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class AgentMovementShortestPathStrategyTest {

    @Test
    void agent_shouldMove_to_smallerDistance_from_safeZone_tile() {
        Map map = TestMaps.mapFrom(
                "@@@@@@",
                "@...S@",
                "@....@",
                "@@@@@@"
        );
        Agent agent = new Agent(new Position(1, 2), new ShortestPathStrategy());
        List<Agent> agents = new ArrayList<>(List.of(agent));
        AgentMovementService movement = new AgentMovementService();
        movement.moveAgents(map, agents);
        Position after = agent.getPosition();
        int dx = Math.abs(after.x() - 1);
        int dy = Math.abs(after.y() - 2);
        assertEquals(1, dx + dy);
        MapDistanceCalculator calc = new MapDistanceCalculator();
        int[][] dist = calc.calculate(map, map.getAllTilePositions(Tile.SAFE_ZONE), map::isWalkable);
        int beforeDist = dist[2][1];
        int afterDist = dist[after.y()][after.x()];
        assertTrue(afterDist < beforeDist);
    }

    @Test
    void agent_shouldNot_move_when_safe() {
        Map map = TestMaps.mapFrom(
                "@@@@@",
                "@.S.@",
                "@...@",
                "@@@@@"
        );
        Agent agent = new Agent(new Position(2, 1), new ShortestPathStrategy());
        List<Agent> agents = new ArrayList<>(List.of(agent));
        new AgentMovementService().moveAgents(map, agents);
        assertTrue(agent.isSafe());
        assertEquals(new Position(2, 1), agent.getPosition());
    }
}
