package com.example.volcanoevacuation.services;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.game.Map;
import com.example.volcanoevacuation.strategies.MapDistanceCalculator;
import com.example.volcanoevacuation.game.Position;
import com.example.volcanoevacuation.game.Tile;

import java.util.List;
import java.util.function.Predicate;

public final class LavaSpreadService {

    private final MapDistanceCalculator distanceCalculator = new MapDistanceCalculator();

    public void spreadLava(Map map, List<Agent> agents, int tick, int lavaSpreadPeriodTicks) {
        List<Position> volcanoes = map.getAllTilePositions(Tile.VOLCANO);

        Predicate<Position> canTraverse = p -> lavaCanFlow(map, p);
        int[][] distance = distanceCalculator.calculate(map, volcanoes, canTraverse);

        int spreadRadius = tick / lavaSpreadPeriodTicks;

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (shouldBecomeLava(distance, x, y, spreadRadius)) {
                    Position pos = new Position(x, y);

                    if (canBecomeLava(map, pos)) {
                        map.setTile(pos, Tile.LAVA);
                        burnAgents(agents, pos);
                    }
                }
            }
        }
    }

    private boolean shouldBecomeLava(int[][] distance, int x, int y, int spreadRadius) {
        int d = distance[y][x];
        return d != -1 && d <= spreadRadius;
    }

    private boolean canBecomeLava(Map map, Position pos) {
        Tile current = map.getTile(pos);
        return current != Tile.LAVA
                && current != Tile.VOLCANO
                && current != Tile.SAFE_ZONE
                && current != Tile.BARRICADE;
    }

    private boolean lavaCanFlow(Map map, Position pos) {
        Tile t = map.getTile(pos);
        return t != Tile.BARRICADE
                && t != Tile.BORDER
                && t != Tile.SAFE_ZONE;
    }

    private void burnAgents(List<Agent> agents, Position pos) {
        for (Agent a : agents) {
            if (a.isAlive() && isAt(a, pos)) {
                a.kill();
            }
        }
    }

    private boolean isAt(Agent a, Position pos) {
        Position ap = a.getPosition();
        return ap.x() == pos.x() && ap.y() == pos.y();
    }
}



