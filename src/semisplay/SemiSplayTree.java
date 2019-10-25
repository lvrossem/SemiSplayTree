package semisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {


    private int splaySize;

    private Node<E> tree;


    public SemiSplayTree(int splaySize) {

        this.splaySize = splaySize;
        tree = new Node<>(null, null);

    }

    /**
     *
     * @return de wortel van de boom om testen te kunnen uitvoeren
     */
    public Node<E> getTree() {
        return tree;
    }

    /**
     *
     * @return een iterator die alle toppen van klein naar groot overloopt
     */
    public SemiSplayTreeIterator<E> iterator() {
        return new SemiSplayTreeIterator<E>(tree);
    }

    /**
     *
     * kijkt na of de boom een zekere waarde bevat
     * @return true als het erin zit, anders false
     */
    public boolean contains(E e) {
        return tree.contains(e);
    }

    /**
     *
     * @return aantal toppen in de boom
     */
    public int size() {
        return tree.size();
    }

    /**
     *
     * voegt de opgegeven waarde toe aan de boom als deze er niet in zit
     * @return true als de waarde is toegevoeg, anders false
     */
    public boolean add(E e) {
        if (tree.contains(e)) {
            return false;
        } else if (tree.getValue() == null) {

            tree.setValue(e);
            return true;
        } else {
            tree.add(e);
        }
        return true;
    }

    /**
     *
     * verwijdert de waarde die is meegegeven als deze in de boom zit
     * @return true als de waarde verwijderd is, anders false
     */
    public boolean remove(E e) {
        if (!tree.contains(e)) {
            return false;
        } else {
            return tree.remove(e);
        }
    }

    /**
     * Berekent de diepte van de boom
     * @return diepte van de boom
     */
    public int depth() {
        if (tree == null) {
            return 0;
        } else if (tree.getLeftTree() == null && tree.getRightTree() == null) {
            return 0;
        } else if (tree.getLeftTree() != null && tree.getRightTree() == null) {
            return 1 + tree.getLeftTree().depth();
        } else if (tree.getLeftTree() == null && tree.getRightTree() != null) {
            return 1 + tree.getRightTree().depth();
        } else {
            return Math.max(1 + tree.getLeftTree().depth(), 1 + tree.getRightTree().depth());
        }
    }

}
