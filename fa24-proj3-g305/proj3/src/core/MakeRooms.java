package core;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

import static tileengine.Tileset.*;

public class MakeRooms {
    private int xStart;
    private int yStart;
    private int width;
    private int height;
    public int numWalls = 0;

    public MakeRooms(int xStart, int yStart, int width, int height) {
        this.xStart = xStart;
        this.height = height;
        this.width = width;
        this.yStart = yStart;
    }

    public void makeRooms(TETile[][] world) {
        for (int i = xStart; i < xStart + width; i++) {
            for (int j = yStart; j < yStart + height; j++) {
                if (j == yStart + height - 1 || j == yStart || i == xStart || i == xStart + width - 1) {
                    if (world[i][j] == Tileset.NOTHING) {
                        world[i][j] = Tileset.WALL;
                        numWalls +=1;

                    }

                } else {
                    world[i][j] = Tileset.FLOOR;
                }

            }

        }
    }

    public List<List<Integer>> upperEdge() {
        List<List<Integer>> upperXY = new ArrayList<>();
        for (int i = xStart; i < xStart + width; i++) {
            List<Integer> Set = new ArrayList<>();
            Set.add(i);
            Set.add(yStart + height - 1);
            upperXY.add(Set);
        }
        return upperXY;
    }
    public List<List<Integer>> lowerEdge() {
        List<List<Integer>> upperXY = new ArrayList<>();
        for (int i = xStart; i < xStart + width; i++) {
            List<Integer> Set = new ArrayList<>();
            Set.add(i);
            Set.add(yStart);
            upperXY.add(Set);
        }
        return upperXY;
    }
    public List<List<Integer>> leftEdge() {
        List<List<Integer>> upperXY = new ArrayList<>();
        for (int i = yStart; i < yStart + height; i++) {
            List<Integer> Set = new ArrayList<>();
            Set.add(xStart);
            Set.add(i);
            upperXY.add(Set);
        }
        return upperXY;
    }
    public List<List<Integer>> rightEdge() {
        List<List<Integer>> upperXY = new ArrayList<>();
        for (int i = yStart; i < yStart + height; i++) {
            List<Integer> Set = new ArrayList<>();
            Set.add(xStart + width - 1);
            Set.add(i);
            upperXY.add(Set);
        }
        return upperXY;
    }
    // THIS GIVES YOU A RANDOM POINT FROM THE ROOM EDGES TO CONNECT FROM/TO
    public List<Integer> RandomEdge(Random random) {
        List<List<Integer>> allEdges = new ArrayList<>();
        allEdges.addAll(upperEdge());
        allEdges.addAll(lowerEdge());
        allEdges.addAll(leftEdge());
        allEdges.addAll(rightEdge());

        return allEdges.get(random.nextInt(allEdges.size()));
    }

}