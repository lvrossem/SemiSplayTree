package semisplay;

import java.util.ArrayList;
import java.util.Iterator;

public class SemiSplayTreeCopy implements SearchTree<Integer> {

    public ArrayList<Integer> nodeList;
    private int splaySize;

    public SemiSplayTreeCopy(int splaySize) {
        nodeList = new ArrayList<Integer>();
        this.splaySize = splaySize;

    }


    public boolean add(Integer i) {


        int currentIndex = 0;

        while (true) {

            extend(nodeList, getRightChild(currentIndex) + 1);

            if (nodeList.get(currentIndex) != null) {
                if (i.intValue() < nodeList.get(currentIndex)) {

                    int links = getLeftChild(currentIndex);

                    if (nodeList.get(links) == null) {
                        nodeList.set(links, i);
                        return true;
                    } else {
                        currentIndex = links;

                    }

                } else if (i.intValue() > nodeList.get(currentIndex)) {
                    int rechts = getRightChild(currentIndex);

                    if (nodeList.get(rechts) == null) {
                        nodeList.set(rechts, i);
                        return true;
                    } else {
                        currentIndex = rechts;

                    }
                } else {
                    return false;
                }
            } else {
                nodeList.set(currentIndex, i);
            }
        }
    }

    public void extend(ArrayList<Integer> list, int size) {
        while (list.size() < size) {
            list.add(null);
        }
    }

    public boolean contains(Integer i) {
        return nodeList.contains(i);
    }


    public boolean remove(Integer i) {
        if (!contains(i)) {
            return false;
        } else {
            return true;
        }
    }

    public void splay(int startIndex) {

    }

    public SemiSplayTree getTreeByRoot(int index, SemiSplayTree result) {

        return result;

    }


    public int size() {
        return nodeList.size();
    }



    public int getParent(int index) {
        if (index % 2 == 0) {
            return index / 2 - 1;
        } else {
            return index / 2;
        }
    }

    public int getLeftChild(int index) {
        return index*2 + 1;
    }

    public int getRightChild(int index) {
        return index*2 + 2;
    }


    public int depth() {
        if (nodeList.size() == 0) {
            return 0;
        } else {
            return (int) Math.floor(Math.log(nodeList.size() - 1) / Math.log(2));
        }

    }

    @Override
    public Iterator<Integer> iterator() {
        return nodeList.iterator();
    }

    public static void main(String[] args) {
        SemiSplayTree test = new SemiSplayTree(1);
        test.add(20);
        System.out.println(test.depth());
        test.print();
        test.add(30);
        System.out.println(test.depth());
        test.print();
        test.add(10);
        System.out.println(test.depth());
        test.print();
        test.add(25);
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

    }
}
