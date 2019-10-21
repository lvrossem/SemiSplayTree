package semisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E> {

    public ArrayList<E> nodeList;
    private int splaySize;

    public SemiSplayTree(int splaySize) {
        nodeList = new ArrayList<>();
        this.splaySize = splaySize;

    }


    public boolean add(E e) {

        int currentIndex = 0;

        while (true) {

            extend(nodeList, getRightChild(currentIndex) + 1);

            if (nodeList.get(currentIndex) != null) {
                if (e.compareTo(nodeList.get(currentIndex)) < 0) {

                    int links = getLeftChild(currentIndex);

                    if (nodeList.get(links) == null) {
                        nodeList.set(links, e);
                        splay(nodeList.indexOf(e));
                        return true;
                    } else {
                        currentIndex = links;

                    }

                } else if (e.compareTo(nodeList.get(currentIndex)) > 0) {
                    int rechts = getRightChild(currentIndex);

                    if (nodeList.get(rechts) == null) {
                        nodeList.set(rechts, e);
                        splay(nodeList.indexOf(e));
                        return true;
                    } else {
                        currentIndex = rechts;

                    }
                } else {
                    splay(nodeList.indexOf(e));
                    return false;
                }
            } else {
                nodeList.set(currentIndex, e);
                return true;
            }
        }

    }

    public int lastNotNullIndex() {
        int result = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i) != null) {
                result = i;
            }
        }
        return result;
    }


    public void extend(ArrayList<E> list, int size) {
        while (list.size() < size) {
            list.add(null);
        }
    }

    public boolean contains(E e) {
        return nodeList.contains(e);
    }


    public boolean remove(E e) {
        if (!contains(e)) {
            return false;
        } else {

            int index = nodeList.indexOf(e);
            int leftChild = getLeftChild(index);
            int rightChild = getRightChild(index);

            ArrayList<Integer> leftTree = getTreeByRoot(leftChild, new ArrayList<Integer>());
            ArrayList<Integer> rightTree = getTreeByRoot(rightChild, new ArrayList<Integer>());

            nodeList.set(index, null);

            if (nodeList.get(rightChild) != null && nodeList.get(leftChild) == null) {

                moveUpwards(rightTree);

            } else if (nodeList.get(rightChild) == null && nodeList.get(leftChild) != null) {

                moveUpwards(leftTree);

            } else if (nodeList.get(rightChild) != null && nodeList.get(leftChild) != null) {

                if (depthByList(leftTree) <= depthByList(rightTree)) {

                    moveUpwards(rightTree);

                } else {

                    moveUpwards(leftTree);

                }

            }
        }
        return true;
    }

    public void moveUpwards(ArrayList<Integer> indices) {
        int root = indices.get(0);
        int left = getLeftChild(root);
        int right = getRightChild(root);

        ArrayList<Integer> leftTree = getTreeByRoot(left, new ArrayList<Integer>());
        ArrayList<Integer> rightTree = getTreeByRoot(right, new ArrayList<Integer>());

        int leftDepth = depthByList(leftTree);
        int rightDepth = depthByList(rightTree);

        nodeList.set(getParent(indices.get(0)), nodeList.get(root));
        nodeList.set(root, null);

        if (nodeList.size() > left) {

            if (nodeList.size() > right) {

                if (nodeList.get(left) == null && nodeList.get(right) != null) {

                    moveUpwards(rightTree);

                } else if (nodeList.get(left) != null && nodeList.get(right) == null) {

                    moveUpwards(leftTree);

                } else if (nodeList.get(left) != null && nodeList.get(right) != null) {

                    if (rightDepth <= leftDepth) {

                        moveUpwards(leftTree);

                    } else {

                        moveUpwards(rightTree);

                    }

                }

            } else {

                if (nodeList.get(left) != null) {

                    moveUpwards(leftTree);

                }

            }
        }

    }

    public void splay(int startIndex) {
        //TODO: splay implementeren
    }

    public ArrayList<Integer> getTreeByRoot(int index, ArrayList<Integer> result) {
        result.add(index);
        if (nodeList.size() > 2*index + 1) {

            result.addAll(getTreeByRoot(getLeftChild(index), new ArrayList<>()));

        }

        if (nodeList.size() > 2*index + 2) {

            result.addAll(getTreeByRoot(getRightChild(index), new ArrayList<>()));

        }

        Collections.sort(result);
        return result;

    }


    public int size() {
        int result = 0;
        for (E e: nodeList) {
            if (e != null) {
                result++;
            }
        }

        return result;
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

    public int depthByList(ArrayList<Integer> list) {
        if (list.size() == 0) {
            return 0;
        } else {
            return (int) Math.floor(Math.log(list.size() - 1) / Math.log(2));
        }
    }

    @Override
    public Iterator<E> iterator() {
        return nodeList.iterator();
    }

    public static void main(String[] args) {
        SemiSplayTree test = new SemiSplayTree(1);
        test.add(20);
        System.out.println(test.size());
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(30);
        System.out.println(test.size());
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(10);
        System.out.println(test.size());
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(25);
        System.out.println(test.size());
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(15);
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(5);
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(7);
        System.out.println(test.depth());
        System.out.println(test.nodeList);
        test.add(8);
        System.out.println(test.nodeList);
        System.out.println(test.depth());

        test.remove(7);
        System.out.println(test.nodeList);



    }
}
