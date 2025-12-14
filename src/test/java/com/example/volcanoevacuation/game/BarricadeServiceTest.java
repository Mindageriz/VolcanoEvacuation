package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.services.BarricadeService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class BarricadeServiceTest {

    @Test
    void breakBarricade_should_transformTile_and_decrease_counter() {
        Map map = TestMaps.mapFrom(
                "@@@",
                "@#@",
                "@@@"
        );

        BarricadeService service = new BarricadeService();
        BarricadeService.Counter breaks = new BarricadeService.Counter(1);
        boolean ok = service.breakBarricade(map, new Position(1, 1), breaks);
        assertTrue(ok);
        assertEquals(Tile.ROAD, map.getTile(new Position(1, 1)));
        assertEquals(0, breaks.get());
    }

    @Test
    void placeBarricade_should_transformTile_and_decrease_counter() {
        Map map = TestMaps.mapFrom(
                "@@@",
                "@.@",
                "@@@"
        );
        BarricadeService service = new BarricadeService();
        BarricadeService.Counter places = new BarricadeService.Counter(1);
        boolean ok = service.placeBarricade(map, new Position(1, 1), places);
        assertTrue(ok);
        assertEquals(Tile.BARRICADE, map.getTile(new Position(1, 1)));
        assertEquals(0, places.get());
    }
}
