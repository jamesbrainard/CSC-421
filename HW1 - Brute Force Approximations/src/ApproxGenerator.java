import java.util.Scanner;
import java.io.File;
import java.util.Random;

/**
 * Class that reads in and stores the contents of a text file.
 *   @author Dave Reed
 *   @version 8/20/24
 */
public class ApproxGenerator {
    private String cleanText;
    private Random randy;

    /**
     * Constructs a FileProcessor object that reads in text from a file. The
     * text is processed to remove non-letters and adjacent spaces, and letters
     * are converted to uppercase.
     *   @param fileName the file containing the text
     */
    public ApproxGenerator(String fileName) throws Exception {
        this.cleanText = "";
        this.randy = new Random();

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
        this.cleanText = this.cleanText.trim().replaceAll("\\s+", " ");
        infile.close();       
    }

    /**
     * Generates a string of the specified order and length, matching the 
     * statistical distribution of letters and spaces as the specified file.
     *   @param order the order for the approximation
     *   @param numChars the length of the generated string
     *   @return the generated string
     */
    public String generate(int numChars) {
        String newText = "";
        while (newText.length() < numChars) {
            int index = this.randy.nextInt(this.cleanText.length());
            newText += this.cleanText.charAt(index);
        }
        return newText;
    }
}