package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.strategies.ShortestPathStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class AgentStatusTest {

    @Test
    void kill_shouldMake_agent_dead_and_notSafe_and_notInDanger() {
        Agent a = new Agent(new Position(1, 1), new ShortestPathStrategy());
        a.markSafe();
        a.kill();
        assertFalse(a.isAlive());
        assertFalse(a.isSafe());
        assertFalse(a.isInDanger());
    }

    @Test
    void markSafe_shouldDo_nothing_if_dead() {
        Agent a = new Agent(new Position(1, 1), new ShortestPathStrategy());
        a.kill();
        a.markSafe();
        assertFalse(a.isSafe());
    }
}

