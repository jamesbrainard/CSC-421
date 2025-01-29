/**
 * Class that represents a Sudoku Puzzle.
 *   @author Dave Reed
 *   @author James Brainard
 *   @version 11/11/24
 */
public class Sudoku extends Puzzle {
    
    /**
     * Constructs a Sudoku puzzle by reading the puzzle configuration from a file
     * 
     * @param filename the file containing the configuration
     * @throws java.io.FileNotFoundException if the specified file is not found
     */
    public Sudoku(String filename) throws java.io.FileNotFoundException {	
        super(filename);
    }

    /**
     * Checks if placing a given value at a specified row and column positions
     * causes a conflict according to Sudoku rules.
     * 
     * @param row the row index where the value is to be placed
     * @param col the col index where the value is to be placed
     * @param value the character value to be checked for conflicts
     * @return true if a conflict is detected, false otherwise
     */
    @Override
    public boolean hasConflict(int row, int col, char value) {
    	// Checks for conflicts in the row and column simultaneously
        for (int i = 0; i < numCols; i++) {
            if (grid[row][i] == value || grid[i][col] == value) {
                return true;
            }
        }

        // Calculates the starting indices of the subgrid containing the cell
        int sqrt = (int)Math.sqrt(numCols); // Size of the subgrid
        int boxRowStarting = row - row % sqrt; // Top row of the subgrid
        int boxColStarting = col - col % sqrt; // Leftmost column of the subgrid

        // Checks for conflicts in the subgrid
        for (int i = boxRowStarting; i < boxRowStarting + sqrt; i++) {
            for (int j = boxColStarting; j < boxColStarting + sqrt; j++) {
                if (grid[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }
}