import java.util.Set;

/**
 * Driver class that times the brute force Knapsack solver.
 * @author Dave Reed
 * @version 9/3/24
 *
 */
public class KnapsackTimer {
    public static void main(String[] args) {
    	StopWatch timer = new StopWatch();
    	for (int i = 20; i <= 28; i++) {
    	    Knapsack sack = new Knapsack(50);
    	    for (int j = 0; j < i; j++) {
    	    	sack.addItem(new KnapsackItem(500, 10, "dummy"));
    	    }

    	    timer.start();
    	    Set<KnapsackItem> subset = sack.findOptimalSubset();
    	    timer.stop();
    	    
    	    System.out.println(i + " " + timer.getElapsedTime());
    	}
    }
}