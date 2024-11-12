import java.util.Scanner;
import java.util.Set;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Driver for the nP-hard Knapsack Problem. It reads data from a file,
 * uses the Knapsack to calculate the optimal subset of items, and
 * prints the result.
 *   @author James Brainard
 *   @version 9/15/24
 */
public class KnapsackDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 

        try {
            System.out.println("\nEnter the knapsack weight limit: ");
            int maxWeight = scanner.nextInt();
            Knapsack knapsack = new Knapsack(maxWeight);
            scanner.nextLine();

            System.out.println("Enter the items file: ");
            String filename = scanner.nextLine();

            // Open the file and read item data
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");

                // Ensure the line has at least 3 parts (value, weight, descriptor)
                if (parts.length >= 3) {
                    int value = Integer.parseInt(parts[0]);
                    int weight = Integer.parseInt(parts[1]);

                    // Combine the remaining parts into one descriptor string
                    String descriptor = parts[2];
                    for (int i = 3; i < parts.length; i++) {
                        descriptor += " " + parts[i];
                    }

                    // Error check in case input file has any weights or values of 0
                    if (weight == 0) {
                        System.out.println("WARNING: Item \"" + descriptor + "\" in knapsack has weight 0. Aborting program.");
                        System.exit(0);
                    }
                    if (value == 0) {
                        System.out.println("WARNING: Item \"" + descriptor + "\" in knapsack has value 0. Aborting program.");
                        System.exit(0);
                    }

                    knapsack.addItem(new KnapsackItem(value, weight, descriptor));
                } else {
                    // Print warning if parts.length is less than 3
                    System.out.println("Invalid line format in file: " + line);
                }
            }
            fileScanner.close();

            Set<KnapsackItem> optimalSubset = knapsack.findOptimalSubset();

            // Display the optimal subset and calculate the total value
            int totalValue = 0;
            if (optimalSubset.isEmpty()) {
                System.out.println("No items could be placed into the subset.");
            } else {
                System.out.println("\nOptimal Subset:");
                for (KnapsackItem item : optimalSubset) {
                    System.out.println(item.getDescriptor());
                    totalValue += item.getValue();
                }
                System.out.println("\nTotal Value = $" + totalValue + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }
}
