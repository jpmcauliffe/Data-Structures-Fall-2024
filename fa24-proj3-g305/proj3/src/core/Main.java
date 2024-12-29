package core;
import tileengine.TERenderer;
import core.World;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;
import java.util.Random;
import edu.princeton.cs.algs4.StdDraw;

public class Main {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(World.WIDTH, World.HEIGHT);
        World w = new World(2162074947702758814L);
        System.out.println("CS 61B: The Game");
        System.out.println("Press N to start a new game, L to load a game, or Q to quit.");

        ter.renderFrame(w.getWorld());
        
            // Set up StdDraw canvas
            StdDraw.setCanvasSize(800, 800);
            StdDraw.setXscale(0, 100);
            StdDraw.setYscale(0, 100);

            while (true) {
                drawMainMenu();
                if (StdDraw.hasNextKeyTyped()) {
                    char input = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (input == 'N') {
                        // New Game
                        World world = handleNewGame();
                        if (world != null) {
                            runGame(world);
                        }
                    } else if (input == 'L') {
                        // Load Game (Placeholder)
                        System.out.println("Loading game... (Not implemented)");
                    } else if (input == 'Q') {
                        // Quit the game
                        System.exit(0);
                    }
                }
            }
        }

        private static void drawMainMenu() {
            StdDraw.clear();
            StdDraw.text(50, 75, "CS 61B: The Game");
            StdDraw.text(50, 60, "Press N: New Game");
            StdDraw.text(50, 50, "Press L: Load Game");
            StdDraw.text(50, 40, "Press Q: Quit");
            StdDraw.show();
        }

        private static World handleNewGame() {
            StringBuilder seedBuilder = new StringBuilder();
            while (true) {
                drawSeedInput(seedBuilder.toString());
                if (StdDraw.hasNextKeyTyped()) {
                    char input = StdDraw.nextKeyTyped();
                    if (Character.isDigit(input)) {
                        seedBuilder.append(input); // Append digit to seed
                    } else if (Character.toUpperCase(input) == 'S') {
                        if (seedBuilder.length() > 0) {
                            long seed = Long.parseLong(seedBuilder.toString());
                            return new World(seed); // Create a new World with the entered seed
                        }
                    }
                }
            }
        }

        private static void drawSeedInput(String seed) {
            StdDraw.clear();
            StdDraw.text(50, 75, "Enter Seed:");
            StdDraw.text(50, 50, seed + "_"); // Display the current seed input
            StdDraw.text(50, 25, "Press 'S' to Start");
            StdDraw.show();
        }

        private static void runGame(World world) {
            TERenderer ter = new TERenderer();
            ter.initialize(World.WIDTH, World.HEIGHT);
            ter.renderFrame(world.getWorld());

            // Interactivity loop
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char input = Character.toUpperCase(StdDraw.nextKeyTyped());
                    if (input == 'Q') {
                        System.out.println("Game Over!");
                        break; // Quit game
                    } else if ("WASD".indexOf(input) >= 0) {
                        world.moveAvatar(input); // Move avatar based on input
                        ter.renderFrame(world.getWorld()); // Re-render the updated world
                    }
                }
                StdDraw.pause(100); // Prevent excessive CPU usage
            }
        }
    }
