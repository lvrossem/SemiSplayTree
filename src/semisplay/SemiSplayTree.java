package semisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {


    private int splaySize;
    private Node<E> tree;


    public SemiSplayTree(int splaySize) {

        this.splaySize = splaySize;
        tree = new Node<>(null, null);

    }

    public Iterator<E> iterator() {
        return new ArrayList<E>().iterator();
    }

    public boolean contains(E e) {
        return tree.contains(e);
    }

    public int size() {
        return tree.size();
    }

    public boolean add(E e) {
        if (tree.contains(e)) {
            return false;
        } else {
            return tree.add(e);
        }
    }

    public boolean remove(E e) {
        if (!tree.contains(e)) {
            return false;
        } else {
            return tree.remove(e);
        }
    }

    public int depth() {
        if (tree.getLeftTree() == null && tree.getRightTree() == null) {
            return 0;
        } else if (tree.getLeftTree() != null && tree.getRightTree() == null) {
            return tree.getLeftTree().depth();
        } else if (tree.getLeftTree() == null && tree.getRightTree() != null) {
            return tree.getRightTree().depth();
        } else {
            return Math.max(1 + tree.getLeftTree().depth(), 1 + tree.getRightTree().depth());
        }
    }

    public void print() {
        tree.print();
    }


    public static void main(String[] args) {
        SemiSplayTree test = new SemiSplayTree(1);
        test.add(20);
        System.out.println(test.size());
        System.out.println(test.depth());
        test.print();
        test.add(30);
        System.out.println(test.size());
        System.out.println(test.depth());
        test.print();
        test.add(10);
        System.out.println(test.size());
        System.out.println(test.depth());
        test.print();
        test.add(25);
        System.out.println(test.size());
        System.out.println(test.depth());
        test.print();
        test.add(15);
        System.out.println(test.depth());
        test.print();
        test.add(5);
        System.out.println(test.depth());
        test.print();
        test.add(7);
        System.out.println(test.depth());
        test.print();
        test.add(8);
        test.print();
        System.out.println(test.depth());
        test.add(3);
        test.print();
        System.out.println(test.depth());

        test.remove(7);
        test.print();




    }


}
