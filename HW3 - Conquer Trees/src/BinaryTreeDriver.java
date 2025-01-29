import java.util.List;

public class BinaryTreeDriver {
    public static void main(String[] args) {
        // BinaryTree<Integer> tree = new BinaryTree<>();

        // System.out.println("Adding elements to the tree...");
        // tree.add(7);
        // tree.add(2);
        // tree.add(12);
        // tree.add(2);
        // tree.add(5);
        // tree.add(6);
        // tree.add(99);
        // tree.add(102);

        // System.out.println("Print out the tree as a list with asList... ");
        // System.out.println(tree.asList());

        // System.out.println("Print out the tree as a list with toString...");
        // System.out.println(tree.toString());

        // System.out.println("Size of the tree: " + tree.size());
        // System.out.println("Height of the tree: " + tree.height());

        // tree.remove(99);
        // System.out.println("Size after removal: " + tree.size());
        // System.out.println("Height after removal: " + tree.height());

        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        // Add elements to the tree
        tree.add(7);
        tree.add(2);
        tree.add(12);
        tree.add(1);
        tree.add(5);
        tree.add(6);
        tree.add(99);
        tree.add(88);

        for(int i = 0; i < 18; i++) {
            tree.add(i);
            System.out.println("Tree:");
            System.out.println(tree);
            System.out.println("Size: " + tree.size());
            System.out.println("Height: " + tree.height());
        }
    }
}
