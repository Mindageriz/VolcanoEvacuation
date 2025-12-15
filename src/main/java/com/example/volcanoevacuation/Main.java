package com.example.volcanoevacuation;

import com.example.volcanoevacuation.game.Game;
import com.example.volcanoevacuation.game.Position;

import java.util.Scanner;

public class Main {

    private static final String CMD_QUIT = "q";
    private static final String CMD_BREAK = "b";
    private static final String CMD_PLACE = "p";
    private static final String COORD_SEPARATOR = ";";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            printWelcome(scanner);
            Game game = new Game();
            game.initGame();
            game.spawnAgents();
            int totalAgents = game.agentsInGame();
            label:
            while (true) {
                game.renderGame();
                printMenu();
                String input = scanner.nextLine().trim().toLowerCase();
                switch (input) {
                    case CMD_QUIT:
                        System.out.println("Exiting...");
                        break label;
                    case CMD_BREAK:
                        handleBreak(scanner, game);
                        continue;
                    case CMD_PLACE:
                        handlePlace(scanner, game);
                        continue;
                }

                game.tick();
                if (game.isGameOver()) {
                    printGameOver(game, totalAgents);
                    break;
                }
            }
        }
    }

    private static void printWelcome(Scanner scanner) {
        System.out.println("Welcome to Volcano Evacuation!");
        System.out.println("Press ENTER to begin...");
        scanner.nextLine();
    }

    private static void printMenu() {
        System.out.println("_______________________________");
        System.out.println("Press ENTER for next step");
        System.out.println("Type 'b' to break a barricade");
        System.out.println("Type 'p' to place a barricade");
        System.out.println("Type 'q' to quit");
    }

    private static void handleBreak(Scanner scanner, Game game) {
        if (game.getToBreak() <= 0) {
            System.out.println("You have no barricade breaks left!");
            return;
        }

        System.out.println("Enter barricade coords (x;y)");
        Position pos = readPosition(scanner);
        if (pos == null) return;

        if (game.breakBars(pos)) {
            System.out.println("Barricade at " + pos.x() + " " + pos.y() + " OBLITERATED");
        } else {
            System.out.println(pos.x() + " " + pos.y() + "? not a barricade >:(");
        }
    }

    private static void handlePlace(Scanner scanner, Game game) {
        if (game.getToPlace() <= 0) {
            System.out.println("You have no barricade places left!");
            return;
        }

        System.out.println("Enter road coords (x;y)");
        Position pos = readPosition(scanner);
        if (pos == null) return;

        if (game.placeBars(pos)) {
            System.out.println("Barricade at " + pos.x() + " " + pos.y() + " PLACED");
        } else {
            System.out.println(pos.x() + " " + pos.y() + "? must not be a road huh :O");
        }
    }

    private static Position readPosition(Scanner scanner) {
        String line = scanner.nextLine().trim();
        String[] coords = line.split(COORD_SEPARATOR);

        if (coords.length != 2) {
            System.out.println("Invalid format. Use x;y (example: 10;3)");
            return null;
        }

        try {
            int x = Integer.parseInt(coords[0].trim());
            int y = Integer.parseInt(coords[1].trim());
            return new Position(x, y);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numbers. Use x;y (example: 10;3)");
            return null;
        }
    }

    private static void printGameOver(Game game, int totalAgents) {
        game.renderGame();
        System.out.println("GAME OVER");

        int dead = game.agentsDead();
        int safe = game.agentsSafe();

        if (totalAgents == dead) {
            System.out.println("All " + dead + " agents have died...");
        } else if (totalAgents == safe) {
            System.out.println("All " + safe + " agents are safe!");
        } else {
            System.out.println(safe + " Agents are safe, but " + dead + " agents are dead...");
        }
    }
}

