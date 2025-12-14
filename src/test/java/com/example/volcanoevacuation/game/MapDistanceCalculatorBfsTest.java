package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.strategies.MapDistanceCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class MapDistanceCalculatorBfsTest {

    @Test
    void bfsDistances_should_increaseByOne_per_availableStep() {
        Map map = TestMaps.mapFrom(
                "@@@@@",
                "@...@",
                "@...@",
                "@...@",
                "@@@@@"
        );
        MapDistanceCalculator calc = new MapDistanceCalculator();
        int[][] d = calc.calculate(map, List.of(new Position(1, 1)), map::isWalkable);
        assertEquals(0, d[1][1]);
        assertEquals(1, d[1][2]);
        assertEquals(1, d[2][1]);
        assertEquals(2, d[2][2]);
        assertEquals(2, d[1][3]);
        assertEquals(2, d[3][1]);
        assertEquals(-1, d[0][0]);
        assertEquals(-1, d[0][1]);
        assertEquals(-1, d[4][4]);
    }

    @Test
    void bfsDistances_shouldNot_passThrough_or_beOn_barricades() {
        Map map = TestMaps.mapFrom(
                "@@@@@@@",
                "@...#.@",
                "@...#.@",
                "@...#.@",
                "@@@@@@@"
        );

        MapDistanceCalculator calc = new MapDistanceCalculator();
        int[][] d = calc.calculate(map, List.of(new Position(1, 1)), map::isWalkable);
        assertEquals(0, d[1][1]);
        assertEquals(2, d[1][3]);
        assertEquals(-1, d[1][4]);
        assertEquals(-1, d[2][4]);
        assertEquals(-1, d[3][4]);
        assertEquals(-1, d[1][5]);
        assertEquals(-1, d[2][5]);
        assertEquals(-1, d[3][5]);
    }
}
