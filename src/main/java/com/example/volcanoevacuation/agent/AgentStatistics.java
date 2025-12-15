package com.example.volcanoevacuation.agent;

import java.util.List;
import java.util.function.Predicate;

public final class AgentStatistics {

    private int countAgents(List<Agent> agents, Predicate<Agent> condition) {
        int count = 0;
        for (Agent a : agents) {
            if (condition.test(a)) {
                count++;
            }
        }
        return count;
    }

    public int panickedCount(List<Agent> agents) {
        return countAgents(agents, a -> a.isAlive() && !a.isSafe());
    }

    public int safeCount(List<Agent> agents) {
        return countAgents(agents, Agent::isSafe);
    }

    public int deadCount(List<Agent> agents) {
        return countAgents(agents, a -> !a.isAlive());
    }
}
