/**
 * Represents an item that can be placed in the knapsack.
 * Each item has a value, weight, and descriptor.
 *   @author James Brainard
 *   @version 9/15/24
 */
public class KnapsackItem {
    private int value;
    private int weight;
    private String descriptor;

    /**
     * Constructs a new KnapsackItem object with the given value, weight, and descriptor.
     * @param value the monetary value of the item
     * @param weight the weight of the item
     * @param descriptor the description of the item
     */
    public KnapsackItem(int value, int weight, String descriptor) {
        this.value = value;
        this.weight = weight;
        this.descriptor = descriptor;
    }

    /**
     * Gets the value of the item.
     * 
     * @return the value of the item
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the weight of the item.
     * 
     * @return the weight of the item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the descriptor of the item.
     * 
     * @return the descriptor of the item
     */
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * Returns a string representation of the item.
     * 
     * @return a string representation of the item including its descriptor, value, and weight
     */
    @Override
    public String toString() {
        return "Item: " + descriptor + ", Value: $" + value + ", Weight: " + weight + "lbs";
    }
}