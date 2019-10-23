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
        return current != null;
    }

    public E next() {
        E result = null;

        if (visited.contains(current)) {
            if (current.getParent() != null) {
                current = current.getParent();
                return next();
            } else {
                current = null;
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
            } else {
                current = null;
            }
        }


        return result;

    }


    public void goToMin(Node<E> n) {
        current = n.min();
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
