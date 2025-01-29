import java.util.List;
import java.util.ArrayList;

/**
 * Generic class that implements a binary tree structure.
 *   @author Dave Reed
 *   @author James Brainard
 *   @version 10/3/24
 */
public class BinaryTree<T> {    
    protected TreeNode<T> root;

    /**
     * Constructs an empty binary tree.
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Adds a value to the binary tree (at a random  location).
     *   @param value the value to be added
     */
    public void add(T value) {
        this.root = this.add(this.root, value);
    }
    /**
     * Helper method to recursively add a value to the binary tree
     * @param current the current node in the binary tree
     * @param value the value to be added
     * @return the updated tree node after the addition
     */
    private TreeNode<T> add(TreeNode<T> current, T value) {
        if (current == null) {
            current = new TreeNode<T>(value, null, null);
        }
        else if (Math.random() < 0.5) {
            current.setLeft(this.add(current.getLeft(), value)); 
        }
        else {
            current.setRight(this.add(current.getRight(), value));
        }
        return current;
    }

    /**
     * Determines the size of the binary tree.
     *   @return the size (number of nodes in the tree)
     */
    public int size() {
        return this.size(this.root);
    }
    /**
     * Helper method to determine the size of the binary tree
     * @param current the current node in the binary tree
     * @return the size of the tree rooted at the current node
     */
    private int size(TreeNode<T> current) {
        if (current == null) {
            return 0;
        }
        else {
            return this.root.getSize();
        }
    }

    /**
     * Determines the height of the binary tree.
     *   @return the height (maximum depth) of the tree
     */
    public int height() {
        return this.height(this.root);
    }
    /**
     * Helper method to determine the height of the binary tree
     * @param current the current node in the binary tree
     * @return the height of the tree rooted at the current node
     */
    private int height(TreeNode<T> current) {
        if (current == null) {
            return 0;
        }
        else {
            return this.root.getHeight();
        }
    }
    /**
     * Determines whether the tree contains a particular value.
     *   @param value the value to be searched for
     *   @return true if value is in the tree, otherwise false
     */
    public boolean contains(T value) {
        return this.contains(this.root, value);
    }

    /**
     * Helper method to recursively determine if a value is contained in the binary tree.
     * @param current the current node in the binary tree
     * @param value the value to be searched for
     * @return true if the value is found, false otherwise
     */
    private  boolean contains(TreeNode<T> current, T value) {
        if (current == null) {
            return false;
        }
        else {
            return value.equals(current.getData()) ||
                   this.contains(current.getLeft(), value) ||
                   this.contains(current.getRight(), value);
        }
    }

    /**
     * Removes one occurrence of the specified value.
     *   @param value the value to be removed
     *   @return true if the value was found and removed, else false
     */
    public boolean remove(T value) {
        if (!this.contains(value)) {
            return false;
        }
        else {
            this.root = this.remove(this.root, value);
            return true;
        }
    }
    /**
     * Helper method to recursively remove a value from the binary tree.
     * @param current the current node in the binary tree
     * @param value the value to be removed
     * @return the updated tree node after the removal
     */
    private TreeNode<T> remove(TreeNode<T> current, T value) {
        if (value.equals(current.getData())) {
            if (current.getLeft() == null) {
                current = current.getRight();
            }
            else {
                TreeNode<T> righty = current.getLeft();
                while (righty.getRight() != null) {
                    righty = righty.getRight();
                }
                current.setData(righty.getData());
                current.setLeft(this.remove(current.getLeft(), current.getData()));
            }
        }
        else if (this.contains(current.getLeft(), value)) {
            current.setLeft(this.remove(current.getLeft(), value));
        }
        else {
            current.setRight(this.remove(current.getRight(), value));
        }
        return current;
    }

    /**
     * Converts the binary tree into a list using in-order traversla
     * @return a list of values in the binary tree
     */
    public List<T> asList() {
        List<T> result = new ArrayList<>();
        asList(this.root, result);
        return result;
    }

    /**
     * Helper method to recursively convert the binary tree into a list
     * @param current the current node in the binary tree
     * @param result a list storing the values
     */
    private void asList(TreeNode<T> current, List<T> result) {
        if (current == null) {
            return;
        }
        asList(current.getLeft(), result);
        result.add(current.getData());
        asList(current.getRight(), result);
    }
    
    /**
     * Converts the tree to a String using an inorder traversal.
     *   @return the String representation of the tree.
     */
    @Override
    public String toString() {
        List<T> list = asList();
        return list.toString();
    }


    ////////////////////////////////////////////////////////////////////////////
    /// FOR TESTING PURPOSES
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<Integer>();
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