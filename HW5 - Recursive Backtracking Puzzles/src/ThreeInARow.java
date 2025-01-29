/**
 * Class that represents a ThreeInARow Puzzle.
 *   @author James Brainard
 *   @version 11/11/24
 */
public class ThreeInARow extends Puzzle {
    private static final char BLACK = '▣';
    private static final char WHITE = '▢';

    /**
     * Constructs a ThreeInARow puzzle by reading the puzzle configuration from a file
     * 
     * @param filename the file containing the configuration
     * @throws java.io.FileNotFoundException if the specified file is not found
     */
    public ThreeInARow(String filename) throws java.io.FileNotFoundException {
        super(filename);
    }

    /**
     * Checks if placing a given value at a specified row and column positions
     * causes a conflict according to ThreeInARow rules.
     * 
     * @param row the row index where the value is to be placed
     * @param col the col index where the value is to be placed
     * @param value the character value to be checked for conflicts
     * @return true if a conflict is detected, false otherwise
     */
    @Override
    public boolean hasConflict(int row, int col, char value) {
        if (hasThreeConsecutive(row, col, value)) {
            return true;
        }

        if (!isBalanced(row, col,value)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if placing a given value at the specified row and column causes
     * three consecutive tiles of the same color in either the row or column.
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param value the character value (BLACK or WHITE) to be checked for consecutive tiles
     * @return true if three consecutive tiles of the same color are found, false otherwise
     */
    private boolean hasThreeConsecutive(int row, int col, char value) {
        // Temporarily places the value in the specified cell
        grid[row][col] = value;

        // Checks for three consecutive tiles in an entire row
        for (int i = 0; i < numCols - 2; i++) {
            if (grid[row][i] == grid[row][i + 1] && grid[row][i + 1] == grid[row][i + 2] && grid[row][i] != '-') {
                grid[row][col] = '-'; // Backtracking (resets the cell)
                return true;
            }
        }

        // Checks for three consecutive tiles in an entire column
        for (int i = 0; i < numCols - 2; i++) {
            if (grid[i][col] == grid[i + 1][col] && grid[i + 1][col] == grid[i + 2][col] && grid[i][col] != '-') {
                grid[row][col] = '-';
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if placing a given value at the specified row and column
     * maintains the balance of black and white tiles in the row and column.
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param value the character value (BLACK or WHITE) to be checked for balance
     * @return true if the placement maintains balance, false otherwise
     */
    private boolean isBalanced(int row, int col, char value) {
        int rowBlackCount = 0;
        int rowWhiteCount = 0;
        int colBlackCount = 0;
        int colWhiteCount = 0;

        // Counts number of black and white tiles in a row
        // Includes current placement
        for (int i = 0; i < numCols; i++) {
            if (i == col) { // Current placement
                if (value == BLACK) {
                    rowBlackCount++;
                } else if (value == WHITE) {
                    rowWhiteCount++;
                }
            } else { // Pre-existing tiles
                if (grid[row][i] == BLACK) {
                    rowBlackCount++;
                } else if (grid[row][i] == WHITE) {
                    rowWhiteCount++;
                }
            }   
        }

        int maxCount = numCols / 2;
        return rowBlackCount <= maxCount && rowWhiteCount <= maxCount &&
               colBlackCount <= maxCount && colWhiteCount <= maxCount;
    }
}