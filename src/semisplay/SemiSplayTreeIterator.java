package semisplay;

import java.util.ArrayList;
import java.util.Iterator;

public class SemiSplayTreeIterator<E extends Comparable<E>> implements Iterator<E> {


    private Node<E> root;
    private ArrayList<Node<E>> visited;
    private Node<E> current;

    public SemiSplayTreeIterator(Node<E> root) {
        this.root = root;
        //goToMin(root);
        goToMin(root);

        visited = new ArrayList<>();

    }

    public boolean hasNext() {
        Node<E> right = rightMost();
        return !visited.contains(right);
    }

    public E next() {
        E result = null;

        if (visited.contains(current)) {
            if (current.getParent() != null) {
                current = current.getParent();
                return next();
            }
        }

        else if (current.getRightTree() != null && !visited.contains(current.getRightTree())) {
            result = current.getValue();
            visited.add(current);
            goToMin(current.getRightTree());

        } else {
            result = current.getValue();
            if (current.getParent() != null) {
                visited.add(current);
                current = current.getParent();
            }
        }


        return result;

    }


    public void goToMin(Node<E> n) {
        current = n.min();
    }

    public Node<E> rightMost() {
        Node<E> result = root;
        while (result.getRightTree() != null) {
            result = result.getRightTree();
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
