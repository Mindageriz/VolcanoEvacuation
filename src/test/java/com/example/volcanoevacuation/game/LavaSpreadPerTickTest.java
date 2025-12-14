package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.services.LavaSpreadService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class LavaSpreadPerTickTest {

    @Test
    void lava_shouldNot_spread_at_tick0() {
        Map map = TestMaps.mapFrom(
                "@@@@@",
                "@...@",
                "@.V.@",
                "@...@",
                "@@@@@"
        );
        LavaSpreadService lava = new LavaSpreadService();
        List<Agent> agents = new ArrayList<>();
        lava.spreadLava(map, agents, 0, 3);
        assertEquals(Tile.ROAD, map.getTile(new Position(2, 1)));
        assertEquals(Tile.ROAD, map.getTile(new Position(1, 2)));
        assertEquals(Tile.ROAD, map.getTile(new Position(3, 2)));
        assertEquals(Tile.ROAD, map.getTile(new Position(2, 3)));
        assertEquals(Tile.VOLCANO, map.getTile(new Position(2, 2)));
    }

    @Test
    void lava_should_spread_to_neighbourTiles_at_lavaPeriodTick() {
        Map map = TestMaps.mapFrom(
                "@@@@@",
                "@...@",
                "@.V.@",
                "@...@",
                "@@@@@"
        );
        LavaSpreadService lava = new LavaSpreadService();
        List<Agent> agents = new ArrayList<>();
        lava.spreadLava(map, agents, 3, 3);
        assertEquals(Tile.LAVA, map.getTile(new Position(2, 1)));
        assertEquals(Tile.LAVA, map.getTile(new Position(1, 2)));
        assertEquals(Tile.LAVA, map.getTile(new Position(3, 2)));
        assertEquals(Tile.LAVA, map.getTile(new Position(2, 3)));
        assertEquals(Tile.VOLCANO, map.getTile(new Position(2, 2)));
    }
}
