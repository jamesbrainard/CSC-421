import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Driver for generating strings that have the same statistical distribution of 
 * letters and spaces as a specified file.
 *   @author Dave Reed
 *   @version 8/20/24
 */
public class ApproxDriver {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter the file name: ");
        String filename = input.nextLine();
        
        // Ask the user to input a file name; if file is not found, close the program
        ApproxGenerator approx = null;
        try {
            approx = new ApproxGenerator(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check the file name and try again.");
            input.close();
            return;
        }

        // Ask the user to enter order until entry is >= 1
        System.out.print("Enter the desired order (>= 1): ");
        int order = input.nextInt();
        while (order < 1) {
            System.out.println("Invalid order. Please enter a number greater than or equal to 1.");
            System.out.print("Enter the desired order (>= 1): ");
            order = input.nextInt();
        }

        System.out.print("\nEnter the desired string length (negative to quit): ");
        int numChars = input.nextInt();

        // Continue generating and printing strings as long as the user enters a non-negative length
        while (numChars >= 0) {
            System.out.println(approx.generate(order, numChars));
                    
            System.out.print("\nEnter the desired string length (negative to quit): ");
            numChars = input.nextInt();
        }  
        System.out.println("**DONE**");
        input.close();
    }
}
