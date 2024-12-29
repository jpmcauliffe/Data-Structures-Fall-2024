package core;
import tileengine.TETile;
import tileengine.Tileset;
import core.MakeRooms;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class World {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    private TETile[][] world;
    private Random random;
    private List<Room> rooms;
    private int avX;
    private int avY;

    public World(long seed) {
        world = new TETile[WIDTH][HEIGHT];
        random = new Random(seed);
        rooms = new ArrayList<>();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        generateRooms();
        generateHallways(rooms);
    }

    private class Room {
        int bottomLeftX;
        int bottomLeftY;
        int roomWidth;
        int roomHeight;

        public Room(int bottomLeftX, int bottomLeftY, int roomWidth, int roomHeight) {
            this.bottomLeftX = bottomLeftX;
            this.bottomLeftY = bottomLeftY;
            this.roomWidth = roomWidth;
            this.roomHeight = roomHeight;
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

public void generateRoom() {
    int roomWidth = random.nextInt(4, 12);
    int roomHeight = random.nextInt(4, 12);
    int xStart = random.nextInt(1, WIDTH - roomWidth - 1);
    int yStart = random.nextInt(1, HEIGHT - roomHeight - 1);
    if (isRoomPlacementValid(xStart, yStart, roomWidth, roomHeight)) {
        // Vertical walls
        for (int y = yStart; y <= yStart + roomHeight; y++) {
            world[xStart][y] = Tileset.WALL;
            world[xStart + roomWidth][y] = Tileset.WALL;
        }
        // Horizontal walls
        for (int x = xStart; x <= xStart + roomWidth; x++) {
            world[x][yStart] = Tileset.WALL;
            world[x][yStart + roomHeight] = Tileset.WALL;
        }
        // Floors
        for (int x = xStart + 1; x < xStart + roomWidth; x++) { // Ensure no extra boundary overlap
            for (int y = yStart + 1; y < yStart + roomHeight; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
        // Add room to list
        Room room = new Room(xStart + 1, yStart + 1, roomWidth, roomHeight);
        rooms.add(room);
    }
}

    public void generateRooms() {
        int minRoom = 40;
        int maxRoom = 60;
        int numberRooms = random.nextInt(minRoom, maxRoom);
        for (int i = 0; i < numberRooms; i++) {
            generateRoom();
        }
    }

    public void generateHallway(Room roomOut, Room roomIn) {
        int outX = roomOut.bottomLeftX;
        int outY = roomOut.bottomLeftY;
        int inX = roomIn.bottomLeftX;
        int inY = roomIn.bottomLeftY;
        if (inX > outX) {
            for (int i = outX; i <= inX; i++) {
                world[i][outY] = Tileset.FLOOR;
                if (world[i][outY + 1] != Tileset.FLOOR) {
                    world[i][outY + 1] = Tileset.WALL;
                } else {
                    world[i][outY + 1] = Tileset.FLOOR;
                }
                if (world[i][outY - 1] != Tileset.FLOOR) {
                    world[i][outY - 1] = Tileset.WALL;
                } else {
                    world[i][outY - 1] = Tileset.FLOOR;
                }
            }
        }
        if (inX < outX) {
            for (int i = outX; i >= inX; i--) {
                world[i][outY] = Tileset.FLOOR;
                if (world[i][outY + 1] != Tileset.FLOOR) {
                    world[i][outY + 1] = Tileset.WALL;
                } else {
                    world[i][outY + 1] = Tileset.FLOOR;
                }
                if (world[i][outY - 1] != Tileset.FLOOR) {
                    world[i][outY - 1] = Tileset.WALL;
                } else {
                    world[i][outY - 1] = Tileset.FLOOR;
                }
            }
        }

        if (inY > outY) {
            for (int i = outY; i <= inY; i++) {
                world[inX][i] = Tileset.FLOOR;
                if (world[inX + 1][i] != Tileset.FLOOR) {
                    world[inX + 1][i] = Tileset.WALL;
                } else {
                    world[inX + 1][i] = Tileset.FLOOR;
                }
                if (world[inX - 1][i] != Tileset.FLOOR) {
                    world[inX - 1][i] = Tileset.WALL;
                } else {
                    world[inX - 1][i] = Tileset.FLOOR;
                }
            }
        }

        if (inY < outY) {
            for (int i = outY; i >= inY; i--) {
                world[inX][i] = Tileset.FLOOR;
                if (world[inX + 1][i] != Tileset.FLOOR) {
                    world[inX + 1][i] = Tileset.WALL;
                } else {
                    world[inX + 1][i] = Tileset.FLOOR;
                }
                if (world[inX - 1][i] != Tileset.FLOOR) {
                    world[inX - 1][i] = Tileset.WALL;
                } else {
                    world[inX - 1][i] = Tileset.FLOOR;
                }
            }
        }

    }

    public void generateHallways(List listOfRooms) {
        int numberOfRooms = listOfRooms.size();
        for (int i = 0; i < numberOfRooms - 1; i++) {
            generateHallway((Room) listOfRooms.get(i), (Room) listOfRooms.get(i + 1));
        }
    }

private boolean isRoomPlacementValid(int xStart, int yStart, int roomWidth, int roomHeight) {
    for (int x = xStart - 1; x <= xStart + roomWidth + 1; x++) {
        for (int y = yStart - 1; y <= yStart + roomHeight + 1; y++) {
            if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
                return false;
            }
            if (world[x][y] != Tileset.NOTHING) {
                return false;
            }
        }
    }
    return true;
}

    private void placeAvatar() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    avX = x;
                    avY = y;
                    world[avX][avY] = Tileset.AVATAR;
                    return;
                }
            }
        }
    }

        public void moveAvatar(char direction) {
            int newX = avX;
            int newY = avY;

            if (direction == 'W') {
                newY += 1;
            } else if (direction == 'A') {
                newX -= 1;
            } else if (direction == 'S') {
                newY -= 1;
            } else if (direction == 'D') {
                newX += 1;
            }

            if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && world[newX][newY] != Tileset.WALL) {
                world[avX][avY] = Tileset.FLOOR;
                avX = newX;
                avY = newY;
                world[avX][avY] = Tileset.AVATAR;
            }
    }

} // end of program


