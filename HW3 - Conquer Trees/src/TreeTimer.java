import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

// java -Xss128m src\TreeTimer.java

/** Driver class for timing the building of trees.
 *    @author Dave Reed
 *    @version 9/17/24
 */
public class TreeTimer {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.print("Enter the initial tree size: ");
		int start = input.nextInt();
		System.out.print("Double until this size: ");
		int end = input.nextInt();
		System.out.print("(T)reeSet or (B)inarySearchTree? ");
		char treeType = input.next().toUpperCase().charAt(0);
		System.out.print("(R)andom or (S)orted data? ");
		char dataType = input.next().toUpperCase().charAt(0); 
		input.close();

		if (start > end || (treeType != 'T' && treeType != 'B') 
				        || (dataType != 'R' && dataType != 'S')) {
			System.out.println("INVALID INPUT(S)");
		}
		else {
			StopWatch timer = new StopWatch();

			for (int size = start; size <= end; size *= 2) {
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int i = 0; i < size; i++) {
					nums.add(i);
				}
				if (dataType == 'R') {
					Collections.shuffle(nums);
				}

				if (treeType == 'T') {
					TreeSet<Integer> tree = new TreeSet<Integer>();
					timer.start();
					for (int i = 0; i < size; i++) {
						tree.add(nums.get(i));
					}
					timer.stop();
					System.out.println(tree.size() + " " + timer.getElapsedTime());
				}
				else {
					BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
					timer.start();
					for (int i = 0; i < size; i++) {
						tree.add(nums.get(i));
					}
					timer.stop();
					System.out.println(tree.size() + " " + timer.getElapsedTime());
				}
			}
		}
	}
}