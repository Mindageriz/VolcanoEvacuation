package com.example.volcanoevacuation.agent;

import java.util.List;

public final class AgentStatistics {

    public int panickedCount(List<Agent> agents) {
        int count = 0;
        for (Agent a : agents) {
            if (a.isAlive() && !a.isSafe()) {
                count++;
            }
        }
        return count;
    }

    public int safeCount(List<Agent> agents) {
        int count = 0;
        for (Agent a : agents) {
            if (a.isSafe()) {
                count++;
            }
        }
        return count;
    }

    public int deadCount(List<Agent> agents) {
        int count = 0;
        for (Agent a : agents) {
            if (!a.isAlive()) {
                count++;
            }
        }
        return count;
    }
}
