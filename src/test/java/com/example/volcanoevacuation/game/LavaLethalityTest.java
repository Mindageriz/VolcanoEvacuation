package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.services.LavaSpreadService;
import com.example.volcanoevacuation.strategies.ShortestPathStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class LavaLethalityTest {

    @Test
    void lava_shouldKill_agent() {
        Map map = TestMaps.mapFrom(
                "@@@@@",
                "@...@",
                "@.V.@",
                "@...@",
                "@@@@@"
        );
        Agent agent = new Agent(new Position(2, 1), new ShortestPathStrategy());
        List<Agent> agents = new ArrayList<>(List.of(agent));
        LavaSpreadService lava = new LavaSpreadService();
        int tick = 3;
        int period = 3;
        lava.spreadLava(map, agents, tick, period);
        assertEquals(Tile.LAVA, map.getTile(new Position(2, 1)));
        assertFalse(agent.isAlive());
    }
}
