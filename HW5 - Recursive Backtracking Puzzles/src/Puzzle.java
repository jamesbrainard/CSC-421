import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Abstract class that represents a Puzzle.
 *   @author James Brainard
 *   @version 11/11/24
 */
public abstract class Puzzle {
    protected int numRows;                  // Number of rows in the puzzle grid.
    protected int numCols;                  // Number of columns in the puzzle grid.
    protected char[][] grid;                // The puzzle grid.
    protected Set<Character> allowedValues; // Values that can be placed in grid cells.

    /**
     * Constructs a Puzzle object by reading the puzzle file.
     * 
     * @param filename the name of the file containing the puzzle
     * @throws FileNotFoundException if the file is not found
     */
    public Puzzle(String filename) throws java.io.FileNotFoundException {
        readPuzzleFile(filename);
    }

    /**
     * Reads the puzzle configuration from a given file.
     * Initiailzes the allowed values, grid dimensions, and populates
     * the grid with the given initial values.
     * 
     * @param filename the name of the file containing the puzzle config
     * @throws FileNotFoundException if the file is not found
     */
    private void readPuzzleFile(String filename) throws java.io.FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        // Reads allowed values from file
        allowedValues = new HashSet<>();
        String[] values = scanner.nextLine().split(" ");
        for (String value : values) {
            allowedValues.add(value.charAt(0));
        }

        // Reads grid dimensions from file
        String[] dimensions = scanner.nextLine().split(" ");
        this.numRows = Integer.parseInt(dimensions[1]);
        this.numCols = Integer.parseInt(dimensions[0]);

        // Initializes the grid from above config parameters
        grid = new char[numRows][numCols];

        // Populates grid from file
        for (int i = 0; i < numRows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = line.charAt(j * 2);
            }
        }
        scanner.close();
    }

    /**
     * Returns the number of rows in the puzzle grid.
     * 
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in the puzzle grid.
     * 
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Attempts to solve the puzzle by filling values into the grid.
     * 
     * @return true if a solution is found, false otherwise
     */
    public boolean solve() {
        return solve(0, 0);
    }
    /**
     * Helper method to recursively solve the puzzle starting from a given cell.
     * @param row the row index of the current cell
     * @param col the col index oft he current cell
     * @return true if a solution is found, false otherwise
     */
    private boolean solve(int row, int col) {
        // Reurns true once the 'row' parameter goes off the puzzle
        // i.e. when the algorithm reaches the bottom of the grid.
        if (row == numRows) {
            return true;
        }

        // Skips filled cells
        if (grid[row][col] != '-') {
            return next(row, col);
        }

        // Tries placing each allowed value and checks for conflicts
        for (char value : allowedValues) {
            if (!hasConflict(row, col, value)) {
                grid[row][col] = value;
                if(next(row, col)) {
                    return true; // Should only return if solution is found
                }
                grid[row][col] = '-'; // Backtracks if placing 'value' doesn't lead to solution
            }
        }
        return false;
    }

    /**
     * Advances to the next cell in the grid after the current cell is processed.
     * 
     * @param row the row index of the current cell
     * @param col the column index of the current cell
     * @return true if the puzzle is solved, false otherwise
     */
    private boolean next(int row, int col) {
        if (col == numCols - 1) {
            return solve(row + 1, 0);
        } else {
            return solve(row, col + 1);
        }
    }

    /**
     * Abstract method to check if placing a specific value in a cell causes a conflict.
     * Subclass implementation should define puzzle-specific constraints.
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param value the value to place in the cell
     * @return true if a conflict is detected, false otherwise
     */
    public abstract boolean hasConflict(int row, int col, char value);

    /**
     * Returns a string representation of the puzzle grid.
     * 
     * @return the puzzle grid as a string
     */
    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                string += grid[i][j] + " ";
            }
            string += "\n";
        }
        return string;
    }
}
