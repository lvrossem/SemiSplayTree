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

    //TODO verwijderen
    public Node<E> getTree() {
        return tree;
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
        } else if (tree.getValue() == null) {

            tree.setValue(e);
            return true;
        } else {
            tree.add(e);
        }
        return true;
    }

    public boolean remove(E e) {
        if (!tree.contains(e)) {
            return false;
        } else {
            return tree.remove(e);
        }
    }

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

    public void print() {
        tree.print();
    }


    public static void main(String[] args) {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Random RG = new Random(50);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            tree.add(RG.nextInt(10000));
        }

        for (Integer i: tree) {
            list.add(i);
        }

        int result = 0;
        if (isSorted(list) && list.size() > 1 && !containsDuplicates(list)) {
            result = 1;
        }


        for (int i = 0; i < 44; i++) {

            tree.remove(RG.nextInt(10000));

        }

        tree.print();
        int n = RG.nextInt(10000);
        System.out.println("Gaat kapot bij:" + n);
        tree.remove(n);
        tree.print();



        for (Integer i: tree) {
            System.out.println(i);
        }





    }

    public static boolean isSorted(ArrayList<Integer> list)
    {
        boolean sorted = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i)) > 0) {
                sorted = false;
                System.out.println(list.get(i-1) + " en " + list.get(i));
            }
        }

        return sorted;
    }

    public static boolean containsDuplicates(ArrayList<Integer> list) {
        boolean duplicates = false;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i+1))) {
                duplicates = true;
            }
        }
        return duplicates;
    }


}
