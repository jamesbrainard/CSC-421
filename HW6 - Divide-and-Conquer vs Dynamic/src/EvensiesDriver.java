import java.util.Scanner;

/**
 * Driver class that provides a text inferface for interacting
 * with the Evensies class. Users can choose between calculating
 * specific results or testing timings for different methods.
 * @author James Brainard
 * @version 11/30/2024
 */
public class EvensiesDriver {
    /**
     * Main method that prompts the user
     * @param args
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Choose specific round/token values");
        System.out.println("2. Test timings up to a certain number of rounds");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // EXECUTES IF USER CHOOSES SPECIFIC ROUND/TOKEN VALUES
            System.out.print("Enter the number of tokens to start with: ");
            int tokens = scanner.nextInt();
            while (tokens < 0) {
                System.out.print("You cannot start with a negative number of tokens. Please enter a non-negative number: ");
                tokens = scanner.nextInt();
            }

            System.out.print("Enter the number of rounds to play for: ");
            int rounds = scanner.nextInt();
            while (rounds < 0) {
                System.out.print("You cannot play a negative number of rounds. Please enter a non-negative number: ");
                rounds = scanner.nextInt();
            }

            System.out.println("Choose the method:");
            System.out.println("1. Top down");
            System.out.println("2. Bottom-up");
            System.out.println("3. Caching");
            System.out.println("4. All");
            System.out.print("Enter your choice (1-4): ");
            int method = scanner.nextInt();

            // Activates the Evensies game(s)
            switch (method) {
                case 1:
                    System.out.println("Playing Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
                    singleTopDown(tokens, rounds);
                    break;
                case 2:
                    System.out.println("Playing Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
                    singleBottomUp(tokens, rounds);
                    break;
                case 3:
                    System.out.println("Playing Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
                    singleCaching(tokens, rounds);
                    break;
                case 4:
                    System.out.println("Playing Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
                    singleTopDown(tokens, rounds);
                    singleBottomUp(tokens, rounds);
                    singleCaching(tokens, rounds);
                    break;
                default: 
                    System.out.println("Invalid choice. Exiting.");
                    break;
            }
        } else if (choice == 2) {
            // EXECUTES IF USER CHOOSES TIMINGS
            System.out.print("Enter the maximum number of rounds to test timings for: ");
            int maxRounds = scanner.nextInt();
            while (maxRounds < 0) {
                System.out.print("You cannot play a negative number of rounds. Please enter a non-negative number: ");
                maxRounds = scanner.nextInt();
            }

            double tokenMultiplier = -1; // Initialization
            boolean validInput = false; // Initializatiion

            System.out.println("Enter the tokens multiplier (? for help): ");
            while (!validInput) {
                if (scanner.hasNextDouble()) {
                    tokenMultiplier = scanner.nextDouble();
                    if (tokenMultiplier < 0) {
                        System.out.println("Token multiplier cannot be negative. Please enter a positive double or '?' for help: ");
                    } else {
                        validInput = true;
                    }
                } else if (scanner.hasNext()) {
                    // Prints out a help for what token multiplier is
                    String input = scanner.next();
                    if (input.equals("?")) {
                        System.out.println("The token multiplier determines how many tokens are used in each test case, relative to the number of rounds.");
                        System.out.println("For example, a token multiplier of 2 and a round limit of 5 would test:");
                        System.out.println("0 rounds with 0 tokens,");
                        System.out.println("1 rounds with 2 tokens,");
                        System.out.println("2 rounds with 4 tokens,");
                        System.out.println("3 rounds with 6 tokens,");
                        System.out.println("4 rounds with 8 tokens,");
                        System.out.println("5 rounds with 10 tokens.");
                        System.out.println("Note that, while you can enter a double as a token multiplier, token amount will be rounded prior to each calculation.");
                        System.out.println("Enter the tokens multiplier (? for help)");
                    } else {
                        System.out.println("Invalid input. Enter a positive double or '?' for help: ");
                    }
                }
            }

            System.out.println("Choose the method:");
            System.out.println("1. Top down");
            System.out.println("2. Bottom-up");
            System.out.println("3. Caching");
            System.out.println("4. All");
            System.out.print("Enter your choice (1-4): ");
            int method = scanner.nextInt();

            // Activates the Evensies game(s)
            switch (method) {
                case 1: 
                    timingTopDown(maxRounds, tokenMultiplier);
                    break;
                case 2: 
                    timingBottomUp(maxRounds, tokenMultiplier);
                    break;
                case 3: 
                    timingCaching(maxRounds, tokenMultiplier);
                    break;
                case 4:
                    timingAll(maxRounds, tokenMultiplier);
                    break;
                default: 
                    System.out.println("Invalid choice. Exiting.");
                    break;
            }
        } else {
            System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }

    /**
     * Performs a single top-down calculation and prints the result.
     * 
     * @param tokens the number of tokens to start with
     * @param rounds the number of rounds to play
     */
    private static void singleTopDown(int tokens, int rounds) {
        StopWatch timer = new StopWatch();
        timer.start();
        double topDownResult = Evensies.expectedTopDown(tokens, rounds);
        timer.stop();
        System.out.println("Top-Down Result: " + topDownResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");
    }

    /**
     * Performs a single bottom-up calculation and prints the result.
     * 
     * @param tokens the number of tokens to start with
     * @param rounds the number of rounds to play
     */
    private static void singleBottomUp(int tokens, int rounds) {
        StopWatch timer = new StopWatch();
        timer.start();
        double bottomUpResult = Evensies.expectedBottomUp(tokens, rounds);
        timer.stop();
        System.out.println("Bottom-Up Result: " + bottomUpResult + " ||| Found in " + timer.getElapsedTime() + " miliseconds.");
    }

    /**
     * Performs a single caching calculation and prints the result.
     * 
     * @param tokens the number of tokens to start with
     * @param rounds the number of rounds to play
     */
    private static void singleCaching(int tokens, int rounds) {
        StopWatch timer = new StopWatch();
        timer.start();
        double cacheResult = Evensies.expectedCaching(tokens, rounds);
        timer.stop();
        System.out.println("Cache Result: " + cacheResult + "     ||| Found in " + timer.getElapsedTime() + " miliseconds.");
    }

    /**
     * Tests timing for the top-down approach over a range of rounds and tokens.
     * 
     * @param maxRounds the maximum number of rounds to test
     * @param tokenMultiplier the multiplier used to calculate tokens for each round
     */
    private static void timingTopDown(int maxRounds, double tokenMultiplier) {
        StopWatch timer = new StopWatch();

        for (int i = 0; i < maxRounds + 1; i++) {
            int tokens = (int)Math.round(i * tokenMultiplier);
            int rounds = i;
            System.out.println("\nPlaying Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
            timer.start();
            double topDownResult = Evensies.expectedTopDown(tokens, rounds);
            timer.stop();
            System.out.println("Top-Down Result: " + topDownResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");
        }
    }

    /**
     * Tests timing for the bottom-up approach over a range of rounds and tokens.
     * 
     * @param maxRounds the maximum number of rounds to test
     * @param tokenMultiplier the multiplier used to calculate tokens for each round
     */
    private static void timingBottomUp(int maxRounds, double tokenMultiplier) {
        StopWatch timer = new StopWatch();

        for (int i = 0; i < maxRounds + 1; i++) {
            int tokens = (int)Math.round(i * tokenMultiplier);
            int rounds = i;
            System.out.println("\nPlaying Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
            timer.start();
            double bottomUpResult = Evensies.expectedBottomUp(tokens, rounds);
            timer.stop();
            System.out.println("Bottom-Up Result: " + bottomUpResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");
        }
    }

    /**
     * Tests timing for the caching approach over a range of rounds and tokens.
     * 
     * @param maxRounds the maximum number of rounds to test
     * @param tokenMultiplier the multiplier used to calculate tokens for each round
     */
    private static void timingCaching(int maxRounds, double tokenMultiplier) {
        StopWatch timer = new StopWatch();

        for (int i = 0; i < maxRounds + 1; i++) {
            int tokens = (int)Math.round(i * tokenMultiplier);
            int rounds = i;
            System.out.println("\nPlaying Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
            timer.start();
            double cachingResult = Evensies.expectedCaching(tokens, rounds);
            timer.stop();
            System.out.println("Caching Result: " + cachingResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");
        }
    }

    /**
     * Tests timing for all three approaches over a range of rounds and tokens.
     * 
     * @param maxRounds the maximum number of rounds to test
     * @param tokenMultiplier the multiplier used to calculate tokens for each round
     */
    private static void timingAll(int maxRounds, double tokenMultiplier) {
        StopWatch timer = new StopWatch();

        for (int i = 0; i < maxRounds + 1; i++) {
            int tokens = (int)Math.round(i * tokenMultiplier);
            int rounds = i;
            System.out.println("\nPlaying Evensies with " + tokens + " tokens and " + rounds + " rounds: ");
            timer.start();
            double topDownResult = Evensies.expectedTopDown(tokens, rounds);
            timer.stop();
            System.out.println("Top-Down Result: " + topDownResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");

            timer.start();
            double bottomUpResult = Evensies.expectedBottomUp(tokens, rounds);
            timer.stop();
            System.out.println("Bottom-Up Result: " + bottomUpResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");

            timer.start();
            double cachingResult = Evensies.expectedCaching(tokens, rounds);
            timer.stop();
            System.out.println("Caching Result: " + cachingResult + "  ||| Found in " + timer.getElapsedTime() + " miliseconds.");
        }
    }
}
