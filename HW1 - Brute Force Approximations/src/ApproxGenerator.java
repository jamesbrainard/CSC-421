import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class that reads in and stores the contents of a text file.
 *   @author Dave Reed
 *   @version 8/20/24
 */
public class ApproxGenerator {
    private String cleanText; // The clean and processed text from the file
    private Random randy; // Random number generator for generating random text
    private HashMap<String, List<Character>> frequencyMap; //Frequency map for character sequences

    /**
     * Constructs a FileProcessor object that reads in text from a file. The
     * text is processed to remove non-letters and adjacent spaces, and letters
     * are converted to uppercase.
     *   @param fileName the file containing the text
     */
    public ApproxGenerator(String fileName) throws Exception {
        this.cleanText = ""; 
        this.randy = new Random(); 
        this.frequencyMap = new HashMap<>();

        // Reading and cleaning the text from the file
        Scanner infile = new Scanner(new File(fileName));
        while (infile.hasNextLine()) {
            String nextLine = infile.nextLine().toUpperCase();
            for (int i = 0; i < nextLine.length(); i++) {
                char ch = nextLine.charAt(i);
                if (Character.isLetter(ch) || ch == ' ') {
                    this.cleanText += ch;
                }
            }
            this.cleanText += " ";
        }
        // Reducing multiple spaces to single spaces
        this.cleanText = this.cleanText.trim().replaceAll("\\s+", " ");
        infile.close();       
    }

    /**
     * Builds a "frequency map" based on the specified order.
     * The frequency map stores each substring of length (order - 1)
     * as the key, and a list of characters that follow the substring
     * in the original text as the value
     * 
     * @param order length of the key substrings
     */
    public void buildFrequencyMap(int order) {
        // Looping through the cleanText to create keys of length (order - 1)
        // and mapping them to the list of characters that follow them.
        for (int i = 0; i <= this.cleanText.length() - order; i++) {
            String key = this.cleanText.substring(i, i + order - 1);
            char nextChar = this.cleanText.charAt(i + order - 1);
            
            // If this key is not already in the map, add it with an empty list
            if (!this.frequencyMap.containsKey(key)) {
                this.frequencyMap.put(key, new ArrayList<>());
            }

            // Add the next character to the list associated with the key.
            this.frequencyMap.get(key).add(nextChar);
        }
    }

    /**
     * Generates a string of the specified order and length, matching the 
     * statistical distribution of letters and spaces as the specified file.
     *   @param order the order for the approximation
     *   @param numChars the length of the generated string
     *   @return the generated string
     */
    public String generate(int order, int numChars) {
        // Handle case where the requested string length is less than the order
        if (numChars < order) {
            int startIndex = randy.nextInt(this.cleanText.length() - numChars);
            return this.cleanText.substring(startIndex, startIndex + numChars);
        }

        // Build the frequency map for the specified order
        buildFrequencyMap(order);

        // Generate the string using the frequency map
        String newText = "";
        int startingIndex = randy.nextInt(this.cleanText.length() - order + 1);
        String seed = this.cleanText.substring(startingIndex, startingIndex + order - 1);
        newText += seed;

        // Continue adding characters to the new string until it reaches numChars length
        while (newText.length() < numChars) {
            List<Character> possibleChars = this.frequencyMap.get(seed);
            // If no possible characters are found for the seed, reseed randomly
            if (possibleChars == null || possibleChars.isEmpty()) {
                startingIndex = randy.nextInt(this.cleanText.length() - order + 1);
                seed = this.cleanText.substring(startingIndex, startingIndex + order - 1);
                newText += seed;
            } else {
                // Randomly select the next character from the possible characters list
                char nextChar = possibleChars.get(randy.nextInt(possibleChars.size()));
                newText += nextChar;
                seed = newText.substring(newText.length() - order + 1);
            }
        }
        return newText;
    }
}