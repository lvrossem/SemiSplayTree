package semisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Language level staat ingesteld op Java 8 dus het zou moeten compileren
 */

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {


    private int splaySize;

    private Node<E> tree;


    public SemiSplayTree(int splaySize) {

        this.splaySize = splaySize;
        tree = new Node<E>(null, null, splaySize);

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
        if (tree.contains(e)){
            tree = tree.splay(e);
            return true;
        }
        return false;
    }

    /**
     *
     * @return aantal toppen in de boom
     */
    public int size() {
        return tree.size();
    }


    /**
     * Geeft de top met de opgegeven waarde terug en splayt vanaf deze top, dient als lookup-operatie van de splayboom
     */
     public Node<E> getNodeByValue(E e) {
         if (tree.contains(e)) {
             Node<E> result;
             if (tree.getValue().compareTo(e) > 0) {
                 result = tree.getLeftTree().getNodeByValue(e);
                 tree = tree.splay(e);
                 return result;
             } else if (tree.getValue().compareTo(e) < 0) {
                 result = tree.getRightTree().getNodeByValue(e);
                 tree = tree.splay(e);
                 return result;
             } else {
                 return tree;
             }
         }
         return null;

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
            tree = tree.splay(e);

        }
        return true;
    }

    /**
     *
     * verwijdert de waarde die is meegegeven als deze in de boom zit
     * @return true als de waarde verwijderd is, anders false
     */
    public boolean remove(E e) {
        boolean isLeft = true;
        if (!tree.contains(e)) {
            return false;
        } else {
            Node<E> start = tree.getNodeByValue(e);
            if (start.getRightTree() == null && start.getLeftTree() == null) {
                start = start.getParent();
                tree.remove(e);
                tree = tree.splay(start.getValue());
                return true;
            } else {
                if (start.getParent() != null) {
                    if (start.getValue().compareTo(start.getParent().getValue()) > 0) {
                        isLeft = false;
                    }
                    start = start.getParent();
                    tree.remove(e);

                    tree.print();
                    if (isLeft) {
                        tree = tree.splay(start.getLeftTree().getValue());
                    } else {
                        tree = tree.splay(start.getRightTree().getValue());
                    }
                } else {
                    tree.remove(e);
                }
            }

            //tree = tree.getRoot();
            return true;
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

    /*
    public static void main(String[] args) {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(2);
        System.out.println("ADDED 2");
        tree.getTree().print();
        tree.getNodeByValue(12);
        System.out.println("SEARCHED 12");
        tree.getTree().print();
        /*
        tree.add(16);
        tree.add(20);
        tree.add(1);

        tree.remove(16);


        System.out.println("FINAL PRINT");
        tree.getTree().print();



    }
    */

}
