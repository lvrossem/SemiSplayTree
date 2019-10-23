package semisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {


    private int splaySize;
    public Node<E> tree;


    public SemiSplayTree(int splaySize) {

        this.splaySize = splaySize;
        tree = new Node<>(null, null);

    }

    public SemiSplayTreeIterator<E> iterator() {
        return new SemiSplayTreeIterator<E>(tree);
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
            return 1 + tree.getLeftTree().depth();
        } else if (tree.getLeftTree() == null && tree.getRightTree() != null) {
            return 1 + tree.getRightTree().depth();
        } else {
            return Math.max(1 + tree.getLeftTree().depth(), 1 + tree.getRightTree().depth());
        }
    }

    public void print() {
        tree.print();
    }


    public static void main(String[] args) {
        SemiSplayTree<Integer> test = new SemiSplayTree(1);
        Random RG = new Random(11);
        for (int i = 0; i<100; i++) {
            test.add(RG.nextInt(30));
        }


        test.print();

        for (int i = 0; i<10; i++) {
            int d = RG.nextInt(30);
            System.out.println("te verwijderen: " + d);
            test.remove(d);
            test.print();
        }




        test.print();





        System.out.println("Iterator");
        for (Integer i: test) {
            System.out.println(i);
        }





    }


}
