/**
 * Class that represents a Range Puzzle.
 *   @author James Brainard
 *   @version 11/11/24
 */
public class Range extends Puzzle {
    private static final char BLACK = '▣';
    private static final char WHITE = '▢';

    /**
     * Constructs a Range puzzle from a configuration file.
     * 
     * @param filename the file containing the puzzle configuration
     * @throws java.io.FileNotFoundException if the specified file is not found
     */
    public Range(String filename) throws java.io.FileNotFoundException {
        super(filename);
    }

    /**
     * Determines if placing a value in the specified cell would create a conflict
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param char the character value to place in the cell
     * @return true if a conflict is detected, false otherwise
     */
    @Override
    public boolean hasConflict(int row, int col, char value) {
        grid[row][col] = value; // Temporary placement of the value for check

        if (value == BLACK && hasAdjacentBlackTile(row, col)) {
            grid[row][col] = '-'; // Backtracking
            return true;
        }

        if (isGridFullyFilled()) {
            if (!satisfiesAllNumberedTileConstraints()) {
                grid[row][col] = '-'; // Backtracking
                return true;
            }
        }

        grid[row][col] = '-'; // Backtracking
        return false;
    }

    /**
     * Checks if any of the adjacent cells to the specified cell contain a black square
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @return true if adjacent black tile is found, false otherwise
     */
    private boolean hasAdjacentBlackTile(int row, int col) {
        // Directions for adjacency in cardinal directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Iterates through each direction and checks for adjacent black cells
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isInBounds(newRow, newCol) && grid[newRow][newCol] == BLACK) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies that all numbered tiles' reachability constraints are satisfied.
     * 
     * @return true if all numbered tile constraints are met, false otherwise
     */
    private boolean satisfiesAllNumberedTileConstraints() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char cell = grid[i][j];
                if (Character.isDigit(cell)) { // Only check cells with numeric constraints
                    int requiredReachableCount = Character.getNumericValue(cell);
                    int actualReachableCount = countReachableWhiteTiles(i, j);
                    if (actualReachableCount != requiredReachableCount) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Counts the number of reachable white tiles from the specified cell
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @return the count reachable white tiles
     */
    private int countReachableWhiteTiles(int row, int col) {
        int count = 1; // Counts the current tile itself
        
        // Directions for adjacency in cardinal directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            while (isInBounds(newRow, newCol) && grid[newRow][newCol] != BLACK) {
                if (grid[newRow][newCol] == WHITE) {
                    count++;
                }
                newRow += dir[0];
                newCol += dir[1];
            }
        }
        return count;
    }

    /**
     * Checks if all cells in the grid are filled
     * 
     * @return true if the grid is fully filled, false otherwise
     */
    private boolean isGridFullyFilled() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == '-') return false; // Empty cell is found
            }
        }
        return true;
    }

    /**
     * Verifies if the specified row and column indices are within the bounds of the grid
     * 
     * @param row the row index to check
     * @param col the col index to check
     * @return true if the indices are within bounds, false otherwise
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }
}