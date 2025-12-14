package com.example.volcanoevacuation.agent;

import com.example.volcanoevacuation.strategies.CautiousStrategy;
import com.example.volcanoevacuation.strategies.MovementStrategy;
import com.example.volcanoevacuation.game.Position;
import com.example.volcanoevacuation.strategies.ShortestPathStrategy;

import java.util.Random;

public final class AgentFactory {

    private static final int CAUTIOUS_PERCENT = 50;
    private final Random random = new Random();

    public Agent createAgent(Position position) {
        MovementStrategy strategy = chooseStrategy();
        return new Agent(position, strategy);
    }

    private MovementStrategy chooseStrategy() {
        int roll = random.nextInt(100);

        if (roll < CAUTIOUS_PERCENT) {
            return new CautiousStrategy();
        }
        return new ShortestPathStrategy();
    }
}
