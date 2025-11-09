package com.example.volcanoevacuation;

import com.example.volcanoevacuation.game.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Volcano Evacuation!");
        System.out.println("Press ENTER to begin...");
        String input = scanner.nextLine();

        Game game = new Game();
        game.initGame();
        game.spawnAgents();


        int totalAgents = game.agentsInGame();

        while (true) {
            game.renderGame();

            System.out.println("_______________________________");
            System.out.println("Press ENTER for next step");
            System.out.println("Type 'b' to break a barricade");
            System.out.println("Type 'p' to place a barricade");
            System.out.println("Type 'q' to quit");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting...");
                break;
            }
            if (input.equalsIgnoreCase("b")) {
                if (game.getToBreak() <= 0) {
                    System.out.println("You have no barricade breaks left!");
                } else {
                    System.out.println("Enter barricade coords (x;y)");
                    String[] coords = scanner.nextLine().split(";");
                    if(coords.length == 2){
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        Position pos = new Position(x, y);
                        if (game.breakBars(pos)){
                            System.out.println("Barricade at " + x + " " + y + " OBLITERATED");
                        } else {
                            System.out.println(x + " " + y + "? not a barricade >:(");
                        }
                    }
                }
                continue;
            }
            if (input.equalsIgnoreCase("p")) {
                if (game.getToPlace() <= 0) {
                    System.out.println("You have no barricade places left!");
                } else {
                    System.out.println("Enter road coords (x;y)");
                    String [] coords = scanner.nextLine().split(";");
                    if(coords.length == 2){
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        Position pos = new Position(x, y);
                        if (game.placeBars(pos)){
                            System.out.println("Barricade at " + x + " " + y + " PLACED");
                        } else {
                            System.out.println(x + " " + y + "? must not be a road huh :O");
                        }
                    }
                }
                continue;
            }

            game.tick();

            if (game.isGameOver()) {
                game.renderGame();
                System.out.println("GAME OVER");
                if (totalAgents == game.agentsDead()) {
                    System.out.println("All " + game.agentsDead() + " agents have died...");
                } else if (totalAgents == game.agentsSafe()) {
                    System.out.println("All " + game.agentsSafe() + " agents are safe!");
                } else {
                    System.out.println(game.agentsSafe() + " Agents are safe, but " + game.agentsDead() + " agents are dead...");
                }
                break;
            }
        }

        scanner.close();
    }
}


