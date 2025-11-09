package com.example.volcanoevacuation.game;

import java.util.*;

public class Game {
    private Map map;
    private final List<Agent> agents = new ArrayList<>();
    private int tick = 0;
    //private int[][] lavaTimes;

    private static final int LAVA_THICK = 3;
    private int toBreak = 1;
    private int toPlace = 1;

    public int getToBreak() {
        return toBreak;
    }
    public int getToPlace() {
        return  toPlace;
    }



    public void initGame() {
        map = Map.loadMap();
        //resetLavaTimes();
    }

/*
    public void resetLavaTimes() {
        List<Position> volcanos = map.getAllTilePositions(Tile.VOLCANO);
        if (!volcanos.isEmpty()) {
            Position volcano = volcanos.getFirst();
            lavaTimes = BreadthFirstSearch.executeMultiSourceBFS(map, volcanos);
        } else {
            lavaTimes = null;
        }
    }
*/




    public void spawnAgents() {
        List<Position> houses = map.getAllTilePositions(Tile.HOUSE);
        int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };
        for (Position pos : houses) {
            for (int i = 0; i< dx.length; i++) {
                int nx = pos.x + dx[i];
                int ny = pos.y + dy[i];
                Position pos1 = new Position(nx, ny);
                if(map.isWalkable(pos1)) {
                    agents.add(new Agent(pos1));
                }
            }
        }

    }

    public int agentsInGame() {
        int count = 0;
        for (Agent a : agents) {
            if (a.isAlive() && !a.isSafe()) {
                count++;
            }
        }
        return count;
    }
    public int agentsSafe() {
        int count = 0;
        for (Agent a : agents) {
            if (a.isSafe()) {
                count++;
            }
        }
        return count;
    }
    public int agentsDead() {
        int count = 0;
        for (Agent a : agents) {
            if (!a.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public void renderGame() {
        map.printMap(map, agents, agentsInGame(), agentsSafe(), agentsDead(), toBreak, toPlace);
    }

    private boolean isOccupied(Position pos, Agent agent) {
        for (Agent a : agents) {
            if (agent == null || a != agent) {
                if (a.isAlive()) {
                    if (!a.isSafe()) {
                        Position curPos = a.getPosition();
                        if (curPos.x == pos.x && curPos.y == pos.y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public void moveAgents() {
        List<Position> safeZones = map.getAllTilePositions(Tile.SAFE_ZONE);
        int[][] distance = BreadthFirstSearch.executeMultiSourceBFS(map, safeZones);

        int[] dirX = {0, 1, 0, -1};
        int[] dirY = {1, 0, -1, 0};

        for (Agent a : agents) {
            if (a.isAlive()) {
                if (!a.isSafe()) {
                    Position curPos = a.getPosition();
                    int x = curPos.x;
                    int y = curPos.y;

                    if (map.getTile(curPos).equals(Tile.SAFE_ZONE)) {
                        a.setSafe();
                    } else {
                        int bestDist = 9999;
                        Position bestPos = null;

                        for (int i = 0; i < 4; i++) {
                            int nextX = x + dirX[i];
                            int nextY = y + dirY[i];

                            if (nextY >= 0 && nextY < map.getHeight() && nextX >= 0 && nextX < map.getWidth()) {
                                int nextDist = distance[nextY][nextX];
                                if (nextDist != -1) {
                                    Position potPos = new Position(nextX, nextY);

                                    if (map.isWalkable(potPos)) {
                                        if (!isOccupied(potPos, a)) {
                                            if (nextDist < bestDist) {
                                                bestDist = nextDist;
                                                bestPos = potPos;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (bestPos != null) {
                            a.moveTo(bestPos);
                        }
                    }
                }
            }
        }
    }

    public void spreadLava() {

        List<Position> volcanos = map.getAllTilePositions(Tile.VOLCANO);

        int[][] distance = BreadthFirstSearch.executeLavaMultiSourceBFS(map, volcanos);

        int height = map.getHeight();
        int width = map.getWidth();
        int spread = tick / LAVA_THICK;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int dis = distance[i][j];
                Position pos = new Position(j, i);

                if (dis != -1) {
                    if (dis <= spread) {
                        Tile current = map.getTile(pos);
                        if (current != Tile.LAVA && current != Tile.VOLCANO && current != Tile.SAFE_ZONE && current != Tile.BARRICADE) {
                            map.setTile(pos, Tile.LAVA);
                            for (Agent a : agents) {
                                Position agentPos = a.getPosition();
                                if (a.isAlive() && agentPos.x == pos.x && agentPos.y == pos.y) {
                                    a.setDead();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void tick () {
        spreadLava();
        moveAgents();
        tick++;
    }

    public boolean isGameOver() {
        for (Agent agent : agents) {
            if (agent.isAlive() && !agent.isSafe()) { return false; }
        }
        return true;
    }

    public boolean breakBars(Position pos) {
        if (toBreak <= 0) {
            return false;
        }
        Tile tile = map.getTile(pos);
        if (tile == Tile.BARRICADE) {
            map.setTile(pos, Tile.ROAD);
            toBreak--;
            //resetLavaTimes();
            return true;
        }
        return false;
    }
    public boolean placeBars(Position pos) {
        if (toPlace <= 0) {
            return false;
        }
        Tile tile = map.getTile(pos);
        if (tile == Tile.ROAD) {
            map.setTile(pos, Tile.BARRICADE);
            toPlace--;
            //resetLavaTimes();
            return true;
        }
        return false;
    }




}
