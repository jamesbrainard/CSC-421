import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Simple driver for solving puzzles.
 *   @author Dave Reed
 *   @author James Brainard
 *   @version 11/11/24
 */
public class PuzzleDriver {
    /**
     * Main method that drives the program. 
     * Prompts the user to select a puzzle type, reads the puzzle file, and solves the puzzle.
     * 
     * @param args
     * @throws java.io.FileNotFoundException if the file is not found
     */
    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the puzzle type ('S' for Sudoku, '3' for ThreeInARow, 'R' for Range, 'K' for Skyscrapers): ");
        char puzzleType = input.next().charAt(0);

        if (puzzleType != 'S' && puzzleType != '3' && puzzleType != 'R' && puzzleType != 'K') {
            System.out.println("Erorr: Invalid puzzle type.");
            return;
        }

        // Prints out the initial and, if it exists, the solved puzzle.
        try {
            System.out.println("Enter the puzzle file name: ");
            String filename = input.next();
            input.close();
        
            // Declares (but does not initialize) puzzle
            Puzzle puzzle;

            // Determines puzzle type from user input and initializes appropriate puzzle
            if (puzzleType == 'S') {
                puzzle = new Sudoku(filename);
            } else if (puzzleType == '3') {
                puzzle = new ThreeInARow(filename);
            } else if (puzzleType == 'R') {
                puzzle = new Range(filename);
            } else if (puzzleType == 'K') {
                puzzle = new Skyscrapers(filename);
            } else {
                System.out.println("Invalid puzzle type selected.");
                return;
            }

            System.out.println("Initial Puzzle:\n" + puzzle);
            if (puzzle.solve()) {
                System.out.println("Solution found:\n" + puzzle);
            } else {
                System.out.println("No solution possible.\n" + puzzle);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erorr: File not found");
        }
    }
}