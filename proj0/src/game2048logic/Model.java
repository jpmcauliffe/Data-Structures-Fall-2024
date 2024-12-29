package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }


    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        int i = 0;
        int j = 0;
        System.out.println(size()); // gives height (and therefore width) of board
        while (i < size()) {
            j = 0;
            while (j < size()) {
                if (tile(i, j) == null) {
                    return true;
                }
                j++;
            }
            i++;
        }
        // System.out.println(tile(0,0)); // gave me null
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        int i = 0;
        int j = 0;
        while (i < size()) {
            j = 0;
            while (j < size()) {
                if (tile(i, j) == null) {
                    j++;
                } else if (tile(i, j).value() == MAX_PIECE) {
                    return true;
                } else {
                    j++;
                }
            }
            i++;
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        // get value for tile at x, y
        // check if tile at x+1, y equal to value at x, y
        // check if tile at x-1, y equal to value at x, y
        // check if tile at x, y+1 equal to value at x, y
        // check if tile at x, y-1 equal to value at x, y

        //get size of game board
        int size = size();

        // if any empty spaces exist then return true
        // could I just use emptySpaceExists() method?
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tile(i, j) == null) {
                    return true;
                }
            }
        }

        //check adjacent tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tile(i, j) != null) {
                    int currentValue = tile(i, j).value();

                    //x+1
                    if (i < size - 1 && tile(i + 1, j) != null && tile(i + 1, j).value() == currentValue) {
                        return true;
                    }
                    //x-1
                    if (i > 0 && tile(i - 1, j) != null && tile(i - 1, j).value() == currentValue) {
                        return true;
                    }
                    //y+1
                    if (j < size - 1 && tile(i, j + 1) != null && tile(i, j + 1).value() == currentValue) {
                        return true;
                    }
                    //y-1
                    if (j > 0 && tile(i, j - 1) != null && tile(i, j - 1).value() == currentValue) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion (ignoring empty space)
     *    and have the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);

        if (currTile == null) {
            return;
        }
        int myValue = currTile.value();
        int targetY = y;
        int size = size();

        while (targetY + 1 < size) {
            if (board.tile(x, targetY + 1) == null) {
                targetY++;
            } else {
                if (myValue == board.tile(x, targetY + 1).value()) {
                    if (!board.tile(x, y).wasMerged() && !board.tile(x, targetY + 1).wasMerged()) {
                        targetY++;
                        score += board.tile(x, y).value() + board.tile(x, targetY).value();
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        if (targetY != y) {
            board.move(x, targetY, currTile);
        }
    }

    /** Handles the movements of the tilt in column x of board B
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        int size = size();
        for (int j = size - 1; j >= 0; j--) {
            moveTileUpAsFarAsPossible(x, j);
        }
    }

    public void tilt(Side side) {
        int size = size();
        //for (int i = 0; i < size; i++) {
        //    tiltColumn(i);
        //}
        Side s = side;
        board.setViewingPerspective(s);
        for (int i = 0; i < size; i++) {
            tiltColumn(i);
        }
        board.setViewingPerspective(Side.NORTH);
    }

    // Tilts every column of the board toward SIDE.
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}