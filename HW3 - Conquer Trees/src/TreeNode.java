/**
 * Generic TreeNode class for storing nodes in a binary tree.
 *   @author Dave Reed
 *   @author James Brainard
 *   @version 10/3/24
 */
public class TreeNode<T> {
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private int size;
    private int height;

    /**
     * Constructs a node with the specified contents.
     *   @param d the data value to be stored
     *   @param l the left child/subtree
     *   @param r the right child/subtree
     */
    public TreeNode(T d, TreeNode<T> l, TreeNode<T> r) {
        this.data = d;
        this.left = l;
        this.right = r;
        updateFields();
    }

    /**
     * Accessor method for the data value.
     *   @return the data value stored in the node
     */
    public T getData() {
        return this.data;
    }

    /**
     * Accessor method for the left child/subtree.
     *   @return the left child/subtree
     */
    public TreeNode<T> getLeft() {
        return this.left;
    }

    /**
     * Accessor method for the right child/subtree.
     *   @return the right child/subtree
     */
    public TreeNode<T> getRight() {
        return this.right;
    }

    /**
     * Setter method for changing the data value.
     *   @param newData the new data value
     */
    public void setData(T newData) {
        this.data = newData;
    }

    /**
     * Setter method for changing the left child/subtree.
     *   @param newLeft the new left child/subtree
     */
    public void setLeft(TreeNode<T> newLeft) {
        this.left = newLeft;
        updateFields();
    }
    
    /**
     * Setter method for changing the right child/subtree.
     *   @param newRight the new right child/subtree
     */
    public void setRight(TreeNode<T> newRight) {
        this.right = newRight;
        updateFields();
    }

    /**
     * Accessor method for the size of the tree at node.
     * @return this.size the size of the tree at node
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Accessor method for the height of the tree at node.
     * @return this.height the height of the tree at node.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Updates this.size and this.height when called.
     */
    private void updateFields() {
        int leftSize = 0;
        int rightSize = 0;

        if (this.left != null) {
            leftSize = this.left.getSize();
        }

        if (this.right != null) {
            rightSize = this.right.getSize();
        }

        this.size = 1 + leftSize + rightSize;

        int leftHeight = 0;
        int rightHeight = 0;

        if (this.left != null) {
            leftHeight = this.left.getHeight();
        }

        if (this.right != null) {
            rightHeight = this.right.getHeight();
        }

        if (leftHeight >= rightHeight) {
            this.height = 1 + leftHeight;
        } else {
            this.height = rightHeight;
        }
    }
}