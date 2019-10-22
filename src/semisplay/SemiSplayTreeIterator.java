package semisplay;

import java.util.ArrayList;
import java.util.Iterator;

public class SemiSplayTreeIterator<E extends Comparable<E>> implements Iterator<E> {

    private Node<E> current;
    private Node<E> root;
    private ArrayList<Node<E>> visited;

    public SemiSplayTreeIterator(Node<E> root) {
        this.root = root;
        current = root;
        visited = new ArrayList<>();
    }

    public boolean hasNext() {
        return current != null;
    }

    public E next() {
        E result = null;
        if (current.getLeftTree() != null) {
            visited.add(current);
            current = current.getLeftTree();
            result = current.getValue();
        } else if (current.getRightTree() != null) {
            visited.add(current);
            current = current.getRightTree();
            result = current.getValue();
        } else {

            while (current.getParent() != null && (visited.contains(current.getRightTree()) || current.getRightTree() == null)) {
                current = current.getParent();
            }

            if (!visited.contains(current.getRightTree())) {
                current = current.getRightTree();
                visited.add(current);
                result = current.getValue();
            } else if (current.getParent() == null) {
                current = null;
            }

        }
        return result;
    }



    public void iterateOverAll() {
        System.out.println(current.getValue());
        visited.add(current);
        while (hasNext()) {
            System.out.println(next());
        }
        current = root;
        visited.clear();
    }

}
