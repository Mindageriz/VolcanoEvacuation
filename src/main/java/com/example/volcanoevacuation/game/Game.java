package com.example.volcanoevacuation.game;

import com.example.volcanoevacuation.agent.Agent;
import com.example.volcanoevacuation.resources.DefaultMapLoader;
import com.example.volcanoevacuation.services.AgentMovementService;
import com.example.volcanoevacuation.agent.AgentSpawner;
import com.example.volcanoevacuation.agent.AgentStatistics;
import com.example.volcanoevacuation.services.BarricadeService;
import com.example.volcanoevacuation.services.LavaSpreadService;
import com.example.volcanoevacuation.utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int LAVA_SPREAD_TICK_PERIOD = 3;
    private static final int BARRICADE_BREAKS = 1;
    private static final int BARRICADE_PLACES = 1;

    private Map map;
    private final List<Agent> agents = new ArrayList<>();
    private int tick = 0;

    private final AgentStatistics agentStatistics = new AgentStatistics();
    private final AgentSpawner agentSpawner = new AgentSpawner();
    private final BarricadeService barricadeService = new BarricadeService();
    private final AgentMovementService agentMovementService = new AgentMovementService();
    private final LavaSpreadService lavaSpreadService = new LavaSpreadService();
    private final Renderer renderer = new Renderer();


    private final BarricadeService.Counter toBreak = new BarricadeService.Counter(BARRICADE_BREAKS);
    private final BarricadeService.Counter toPlace = new BarricadeService.Counter(BARRICADE_PLACES);

    public void initGame() {
        map = new DefaultMapLoader().load();
    }

    public void spawnAgents() {
        agentSpawner.spawnAroundHouses(map, agents);
    }

    public void renderGame() {
        renderer.render(map, agents, agentsInGame(), agentsSafe(), agentsDead(), getToBreak(), getToPlace()
        );
    }

    public void tick() {
        spreadLava();
        moveAgents();
        tick++;
    }

    public boolean isGameOver() {
        for (Agent a : agents) {
            if (isStillInDanger(a)) {
                return false;
            }
        }
        return true;
    }

    public boolean breakBars(Position pos) {
        return barricadeService.breakBarricade(map, pos, toBreak);
    }

    public boolean placeBars(Position pos) {
        return barricadeService.placeBarricade(map, pos, toPlace);
    }

    public int getToBreak() {
        return toBreak.get();
    }

    public int getToPlace() {
        return toPlace.get();
    }

    public int agentsInGame() {
        return agentStatistics.panickedCount(agents);
    }

    public int agentsSafe() {
        return agentStatistics.safeCount(agents);
    }

    public int agentsDead() {
        return agentStatistics.deadCount(agents);
    }

    private void moveAgents() {
        agentMovementService.moveAgents(map, agents);
    }

    private void spreadLava() {
        lavaSpreadService.spreadLava(map, agents, tick, LAVA_SPREAD_TICK_PERIOD);
    }

    private boolean isStillInDanger(Agent a) {
        return a.isAlive() && !a.isSafe();
    }
}

