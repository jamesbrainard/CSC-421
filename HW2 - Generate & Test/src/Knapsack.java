import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a knapsack that can hold a collection of KnapsackItems
 * Provides methods to add items and find the optimal subset of items
 * based on a given weight limit.
 *   @author James Brainard
 *   @version 9/15/24
 */
public class Knapsack {
    private List<KnapsackItem> items;
    private int maxWeight;

    /**
     * Constructs a new, empty Knapsack.
     */
    public Knapsack(int maxWeight) {
        this.maxWeight = maxWeight;
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the knapsack's list of items.
     * 
     * @param item the item to be added
     */
    public void addItem(KnapsackItem item) {
        items.add(item);
    }

    /**
     * Finds the optimal subset of items that maximizes the total value
     * given the specified weight limit.
     * 
     * @param maxWeight the maximum weight limit of the knapsack
     * @return a Set containing the optimal subset of items
     */
    public Set<KnapsackItem> findOptimalSubset() {
        Set<KnapsackItem> optimalSubset = new HashSet<>(); // To store the best subset found
        int maxValue = 0; // To track the maximum value found

        int numSubsets = (int)Math.pow(2, items.size()); // Calculate the total number of subsets
        for (int i = 0; i < numSubsets; i++) {
            // Create a new subset, tracked for the duration of the loop
            Set<KnapsackItem> subset = new HashSet<>(); 
            int totalWeight = 0;
            int totalValue = 0;

            int bit = i;
            int index = 0;

            // Determine which items are included in the current subset 
            while (bit > 0) {
                // If the rightmost bit is 1, include the item at index
                if (bit % 2 ==1) { 
                    KnapsackItem item = items.get(index);
                    totalWeight += item.getWeight();
                    totalValue += item.getValue();
                    subset.add(item);
                }
                // Shift the bits to the right and move to the next item
                bit /= 2;
                index++;
            }

            // Check if the currently tracked subset is the best found so far
            if (totalWeight <= maxWeight && totalValue > maxValue) {
                // Update the optimalSubset and maxValue with the values of the tracked subset
                maxValue = totalValue;
                optimalSubset = subset;
            }
        }

        return optimalSubset;
    }
}
