package com.example.volcanoevacuation.game;

import java.util.List;

public interface MovementStrategy {
    Position chooseNextPosition(Map map, List<Agent> agents, Agent self, int[][] distanceToSafe);
}

