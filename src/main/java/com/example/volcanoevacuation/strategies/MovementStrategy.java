package com.example.volcanoevacuation.strategies;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.game.Map;
import com.example.volcanoevacuation.game.Position;

import java.util.List;

public interface MovementStrategy {
    Position chooseNextPosition(Map map, List<Agent> agents, Agent self, int[][] distanceToSafe);
}

