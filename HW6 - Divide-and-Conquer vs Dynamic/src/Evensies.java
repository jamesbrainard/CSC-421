import java.util.HashMap;
import java.util.Map;

/**
 * Class that provides methods for calculating expected outcomes of
 * a token-based gambling game using three different approaches: top-down
 * recursion, bottom-up dynamic programming, and recursion with caching.
 * @author James Brainard
 * @version 11/30/2024
 */
public class Evensies {
    // A cache used to store previously computed values for the caching method
    private static Map<String, Double> cache = new HashMap<>();
    // Only really need to account for correct guess and the probability of bottomsies
    private static final double PCORRECT = 0.5;
    private static final double PBOTTOMSIES = 4.0 / 36.0;

    /**
     * Calculates the expected value of tokens after a given number of rounds
     * using a top-down recursive approach.
     * @param tokens the initial number of tokens
     * @param rounds the number of rounds to play
     * @return the expected value of tokens after the specified number of rounds
     */
    public static double expectedTopDown(int tokens, int rounds) {
        // Base case
        if (rounds == 0) {
            return tokens;
        }

        // Calculates and adds expected value for correct and incorrect guesses
        // Best visualized as a binary tree
        double result = 0.0;
        result += PCORRECT * (expectedTopDown(tokens + 1, rounds - 1) - PBOTTOMSIES);
        result += (1 - PCORRECT) * (expectedTopDown(tokens - 1, rounds - 1) - PBOTTOMSIES);

        //return Math.round(result * 100.0) / 100.0;
        return result;
    }

    /**
     * Calculates the expected value of tokens after a given number of rounds
     * using a bottom-up dynamic programming approach.
     * @param tokens the initial number of tokens
     * @param rounds the number of rounds to play
     * @return the expected value of tokens after the specified number of rounds
     */
    public static double expectedBottomUp(int tokens, int rounds) {
        // Wastes some space in dynamic table to allow for negative token values
        int offset = rounds;
        double[][] dynamic = new double[rounds + 1][2 * rounds + tokens + 1];
    
        // Base case
        for (int t = -rounds; t <= tokens + rounds; t++) {
            dynamic[0][t + offset] = t; // Adjusts index by offset to handle negative token values
        }
    
        // Fills in entire table
        for (int r = 1; r <= rounds; r++) {
            for (int t = -rounds; t <= tokens + rounds; t++) {
                // Math for expected correct guess
                if (t + 1 <= tokens + rounds) {
                    dynamic[r][t + offset] += PCORRECT * (dynamic[r - 1][t + 1 + offset] - PBOTTOMSIES);
                }
                // Math for expected incorrect guess
                if (t - 1 >= -rounds) {
                    dynamic[r][t + offset] += (1 - PCORRECT) * (dynamic[r - 1][t - 1 + offset] - PBOTTOMSIES);
                }
            }
        }
    
        // Accesses the desired index of the table
        double result = dynamic[rounds][tokens + offset];
    
        //return Math.round(result * 100.0) / 100.0;
        return result;
    }
    
    /**
     * Calculates the expected value of tokens after a given number of rounds
     * using a recursive approach with caching.
     * @param tokens the initial number of tokens
     * @param rounds the number of rounds to play
     * @return the expected value of tokens after the specified number of rounds
     */
    public static double expectedCaching(int tokens, int rounds) {
        // Base case
        if (rounds == 0) {
            return tokens;
        }

        // Creates a key for the current (tokens, rounds) state and checks to see if it already exists
        // If it does, don't calculate anything, just get the key
        String key = tokens + "," + rounds;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        // Calculates and adds expected value for correct and incorrect guesses
        // Best visualized as a binary tree
        double result = 0.0;
        result += PCORRECT * (expectedCaching(tokens + 1, rounds - 1) - PBOTTOMSIES);
        result += (1 - PCORRECT) * (expectedCaching(tokens - 1, rounds - 1) - PBOTTOMSIES);

        // Stores the computed result in the cache for possible later use
        cache.put(key, result);

        //return Math.round(result * 100.0) / 100.0;
        return result;
    }
}