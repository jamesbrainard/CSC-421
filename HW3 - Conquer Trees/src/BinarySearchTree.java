import java.util.List;

/**
 * Generic class that implements a binary search tree, building upon the 
 * existing BinaryTree class.
 *   @param <T> the type of value stored, must be Comparable<T>
 *   @author Dave Reed 
 *   @author James Brainard
 *   @version 10/3/24
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {
    /**
     * Overrides the super.add method to add according to the BST property.
     * Automatically rebalances the tree if its height exceeds the allowed
     * limit (2 * log2(size)).
     *   @param value the value to be added to the tree
     */
    @Override
    public void add(T value) {
        super.add(value);

        if (this.height() > 1 + 2 * (int)(Math.log(size()) / Math.log(2))) {
            rebalance();
        }
    }

    /**
     * Removes the specified value from the tree and rebalances the tree
     *  @param value the value to be removed
     *  @return true if the value was removed, otherwise false
     */
    @Override
    public boolean remove(T value) {
        boolean wasRemoved = super.remove(value);

        if (this.height() > 1 + 2 * (int)(Math.log(size()) / Math.log(2))) {
            rebalance();
        }
        
        return wasRemoved;
    }

    /**
     * Overrides the super.contains method to take advantage of binary search.
     *   @param value the value to be searched for
     *   @return true if that value is in the tree, otherwise false
     */
    @Override
    public boolean contains(T value) {
        return this.contains(this.root, value);
    }
    /**
     * Helper method to recursively search for a value in the binary search tree
     * @param current the current node in the binary search tree
     * @param value the value to be searched for
     * @return if the value is found, otherwise false
     */
    private boolean contains(TreeNode<T> current, T value) {
        if (current == null) {
            return false;
        }
        else if (value.equals(current.getData())) {
                return true;
        }
        else if (value.compareTo(current.getData()) < 0) {
            return this.contains(current.getLeft(), value);
        }
        else {
            return this.contains(current.getRight(), value);
        }
    }

    /**
     * Rebalances the binary search tree to maintain efficient operations
     */
    private void rebalance() {
        List<T> sortedList = this.asList();
        this.root = buildBalancedTree(sortedList, 0, sortedList.size() - 1);
    }

    /**
     * Helper method to build a balanced binary search tree from a sorted list
     * @param sortedList the sorted list of elements
     * @param startIndex the starting index in the list
     * @param endIndex the ending index in the list
     * @return the root node of the balanced binary tree
     */
    private TreeNode<T> buildBalancedTree(List<T> sortedList, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            return null;
        }

        int mid = (startIndex + endIndex) / 2;
        TreeNode<T> node = new TreeNode<>(sortedList.get(mid), null, null);

        node.setLeft(buildBalancedTree(sortedList, startIndex, mid - 1));
        node.setRight(buildBalancedTree(sortedList, mid + 1, endIndex));

        return node;
    }



    ////////////////////////////////////////////////////////////////////////////
    /// FOR TESTING PURPOSES
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.add(7);
        tree.add(2);
        tree.add(12);
        tree.add(1);
        tree.add(5);
        tree.add(6);
        tree.add(99);
        tree.add(88);
        System.out.println(tree);
        
        System.out.println("size = " + tree.size());
        System.out.println(tree.contains(2) + " " + tree.contains(7)
                                            + " " + tree.contains(8));

        tree.remove(99);
        tree.remove(7);
        System.out.println(tree);
        System.out.println("size = " + tree.size());
        System.out.println(tree.contains(2) + " " + tree.contains(7)
                                            + " " + tree.contains(8));
    }
}