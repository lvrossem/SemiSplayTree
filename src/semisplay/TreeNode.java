package semisplay;

public class TreeNode<E extends Comparable<E>> {

    private int index;
    private E value;

    public TreeNode(int index, E value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public E getValue() {
        return value;
    }
}
