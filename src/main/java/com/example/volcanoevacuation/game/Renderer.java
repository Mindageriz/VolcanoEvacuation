package com.example.volcanoevacuation.game;

import java.util.List;

public final class Renderer {
    private static final int CLEAR_LINES = 50;

    public void render(Map map, List<Agent> agents, int panic, int safe, int dead, int toBreak, int toPlace) {
        clearScreen();
        printXAxisLabels(map);

        for (int y = 0; y < map.getHeight(); y++) {
            printYAxisLabel(map, y);
            printRow(map, agents, y);
            printSidebar(y, panic, safe, dead, toBreak, toPlace);
            System.out.println();
        }
    }

    private void clearScreen() {
        System.out.println("\n".repeat(CLEAR_LINES));
    }

    private void printXAxisLabels(Map map) {
        printXAxisTens(map);
        printXAxisOnes(map);
    }

    private void printXAxisTens(Map map) {
        int width = map.getWidth();
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            if (x == width - 1) {
                System.out.print(" ");
                continue;
            }
            int tens = x / 10;
            System.out.print(tens == 0 ? " " : tens);
        }
        System.out.println();
    }

    private void printXAxisOnes(Map map) {
        int width = map.getWidth();
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            if (x == 0 || x == width - 1) {
                System.out.print(" ");
            } else {
                System.out.print(x % 10);
            }
        }
        System.out.println();
    }

    private void printYAxisLabel(Map map, int y) {
        if (y == 0 || y == map.getHeight() - 1) {
            System.out.print("    ");
        } else {
            System.out.printf("%3d ", y);
        }
    }

    private void printRow(Map map, List<Agent> agents, int y) {
        for (int x = 0; x < map.getWidth(); x++) {
            System.out.print(getRenderedChar(map, agents, x, y));
        }
    }

    private char getRenderedChar(Map map, List<Agent> agents, int x, int y) {
        Tile tile = map.getTile(new Position(x, y));
        char ch = tile.symbol;

        if (tile == Tile.SAFE_ZONE) return ch;

        for (Agent a : agents) {
            Position p = a.getPosition();
            if (p.x() == x && p.y() == y) {
                if (!a.isAlive()) return '+';
                if (!a.isSafe()) return '*';
                return ch;
            }
        }
        return ch;
    }

    private void printSidebar(int y, int panic, int safe, int dead, int toBreak, int toPlace) {
        if (y == 0) System.out.print("          AGENTS: *");
        if (y == 1) System.out.print("          in panic: " + panic);
        if (y == 2) System.out.print("              safe: " + safe);
        if (y == 3) System.out.print("              dead: " + dead);

        if (y == 5) System.out.print("      BARRICADES: #");
        if (y == 6) System.out.print("          to break: " + toBreak);
        if (y == 7) System.out.print("          to place: " + toPlace);
    }
}