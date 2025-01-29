import java.io.File;
import java.util.Scanner;

/**
 * Class that represents a Skyscrapers Puzzle.
 *   @author James Brainard
 *   @version 11/11/24
 */
public class Skyscrapers extends Puzzle {
    private int[] topClues;
    private int[] bottomClues;
    private int[] leftClues;
    private int[] rightClues;

    /**
     * Constructs a Skyscrapers puzzle by reading the puzzle configuration from a file
     * 
     * @param filename the file containing the configuration
     * @throws java.io.FileNotFoundException if the specified file is not found
     */
    public Skyscrapers(String filename) throws java.io.FileNotFoundException {
        super(filename);
        loadClues(filename);
    }

    /**
     * Loads the visibility clues from the specified file.
     * File format must place the clues after the grid configuration.
     * 
     * @param filename the name of the file containing puzzle clues
     * @throws java.io.FileNotFoundException if the specified file is not found
     */
    private void loadClues(String filename) throws java.io.FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        // Skips the lines for allowed values and grid dimensions
        scanner.nextLine();
        scanner.nextLine();
        for (int i = 0; i < numRows; i++) {
            scanner.nextLine();
        }

        // Initializes clue arrays
        topClues = new int[numCols];
        bottomClues = new int[numCols];
        leftClues = new int[numRows];
        rightClues = new int[numRows];

        // Loads the clues from the file
        for (int i = 0; i < numCols; i++) {
            topClues[i] = scanner.nextInt();
        }
        for (int i = 0; i < numCols; i++) {
            bottomClues[i] = scanner.nextInt();
        }
        for (int i = 0; i < numRows; i++) {
            leftClues[i] = scanner.nextInt();
        }
        for (int i = 0; i < numRows; i++) {
            rightClues[i] = scanner.nextInt();
        }
        scanner.close();
    }

    /**
     * Checks if placing a given value at a specified row and column positions
     * causes a conflict according to Skyscrapers rules.
     * 
     * @param row the row index where the value is to be placed
     * @param col the col index where the value is to be placed
     * @param value the character value to be checked for conflicts
     * @return true if a conflict is detected, false otherwise
     */
    @Override
    public boolean hasConflict(int row, int col, char value) {
        int height = value - '0';

        if (!isUniqueInRowAndCol(row, col, height)) {
            return true;
        }

        // Temporarily place the value to be checked for constraints
        grid[row][col] = value;
        boolean conflicts = !satisfiesPartialVisibility(row, col);
        grid[row][col] = '-'; // Backtracking
        return conflicts;
    }

    /**
     * Ensures that the given value is unique within its row and column.
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param height the int value representing the building height
     * @return true if the value is unique within its row and column, false otherwise
     */
    private boolean isUniqueInRowAndCol(int row, int col, int height) {
        // Checks for uniqueness in row
        for (int i = 0; i < numCols; i++) {
            if (grid[row][i] - '0' == height && i != col) {
                return false;
            }
        }
        // Checks for uniqueness in column
        for (int i = 0; i < numRows; i++) {
            if (grid[i][col] - '0' == height && i != row) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the row and column visibility clues are satisfied for a partially filled row or column
     * 
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @return true if the visibility clues are satisfied, false otherwise
     */
    private boolean satisfiesPartialVisibility(int row, int col) {
        // Check row visibility if fully populated
        if (isRowFullyPopulated(row) && !checkRowVisibility(row, leftClues[row], rightClues[row])) {
            return false;
        }
        // Check column visibility if fully populated
        if (isColumnFullyPopulated(col) && !checkColVisibility(col, topClues[col], bottomClues[col])) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a specified row is fulled populated.
     * 
     * @param row the row index to check
     * @return true of the row is fully populated, false otherwise
     */
    private boolean isRowFullyPopulated(int row) {
        for (int col = 0; col < numCols; col++) {
            if (grid[row][col] == '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a specified column is fully populated
     * 
     * @param col the col index to check
     * @return true if the row is fully populated, false otherwise
     */
    private boolean isColumnFullyPopulated(int col) {
        for (int row = 0; row < numRows; row++) {
            if (grid[row][col] == '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * CHecks if the visibility requirements for a row are satisfied
     * 
     * @param row the row index
     * @param leftClue the left clue for the row
     * @param rightClue the right clue for the row
     * @return true if the visibility requirements are met, otherwise false
     */
    private boolean checkRowVisibility(int row, int leftClue, int rightClue) {
        int visibleCount = 0;
        int maxHeight = 0;

        // Checks visibility from the left side
        for (int col = 0; col < numCols; col++) {
            int height = grid[row][col] - '0';
            if (height > maxHeight) {
                maxHeight = height;
                visibleCount++;
            }
        }
        if (leftClue != 0 && visibleCount != leftClue) {
            return false;
        }

        // Checks visibility from the right side
        visibleCount = 0;
        maxHeight = 0;
        for (int col = numCols - 1; col >= 0; col--) {
            int height = grid[row][col] - '0';
            if (height > maxHeight) {
                maxHeight = height;
                visibleCount++;
            }
        }
        return rightClue == 0 || visibleCount == rightClue;
    }

    /**
     * Checks if the visibility requirements for a column are satisfied
     * 
     * @param col the col index
     * @param topClue the top clue for the column
     * @param bottomClue the bottom clue for the column
     * @return true if the visibility requirements are met, false otherwise
     */
    private boolean checkColVisibility(int col, int topClue, int bottomClue) {
        int visibleCount = 0;
        int maxHeight = 0;

        // Checks visibility from the top
        for (int row = 0; row < numRows; row++) {
            int height = grid[row][col] - '0';
            if (height > maxHeight) {
                maxHeight = height;
                visibleCount++;
            }
        }
        if (topClue != 0 && visibleCount != topClue) {
            return false;
        }

        // Checks visibility from the bottom
        visibleCount = 0;
        maxHeight = 0;
        for (int row = numRows - 1; row >= 0; row--) {
            int height = grid[row][col] - '0';
            if (height > maxHeight) {
                maxHeight = height;
                visibleCount++;
            }
        }
        return bottomClue == 0 || visibleCount == bottomClue;
    }
}
