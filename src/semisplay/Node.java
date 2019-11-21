package semisplay;

import java.util.ArrayList;
import java.util.List;

public class Node<E extends Comparable<E>> {

    private Node<E> parent;
    private Node<E> leftTree;
    private Node<E> rightTree;

    private int splaySize;
    private E value;

    public Node(E value, Node parent, int splaySize) {
        this.value = value;
        this.parent = parent;
        this.splaySize = splaySize;
    }

    public E getValue() {
        return value;
    }

    public Node<E> getLeftTree() {
        return leftTree;
    }

    public Node<E> getRightTree() {
        return rightTree;
    }

    public int size() {
        if (rightTree == null && leftTree == null) {
            return 1;
        } else if (rightTree == null && leftTree != null) {
            return 1 + leftTree.size();
        } else if (rightTree != null && leftTree == null) {
            return 1 + getRightTree().size();
        } else {
            return 1 + leftTree.size() + rightTree.size();
        }
    }

    public boolean contains(E e) {
        boolean result;
        if (value == null) {
            result = e == null;
            return result;
        } else if (value.equals(e)) {
            return true;
        } else if (value.compareTo(e) > 0 && leftTree != null) {
            result = leftTree.contains(e);

            return result;
        } else if (value.compareTo(e) < 0 && rightTree != null) {
            result = rightTree.contains(e);

            return result;
        } else {

            return false;
        }
    }

    public Node<E> getRoot() {
        if (parent == null) {
            return this;
        }
        return parent.getRoot();
    }

    public boolean add(E e) {
        if (value == null) {
            value = e;
            return true;
        } else if (e.compareTo(value) < 0) {
            if (leftTree == null) {

                leftTree = new Node(e, this, splaySize);
                //leftTree.splay();
                return true;
            } else {
                return leftTree.add(e);
            }
        } else {
            if (rightTree == null) {

                rightTree = new Node(e, this, splaySize);
                //rightTree.splay();
                return true;
            } else {
                return rightTree.add(e);
            }
        }
    }

    public void addNode(Node<E> newNode) {

        if (newNode != null) {
            if (newNode.getValue().compareTo(value) > 0) {
                if (rightTree == null) {
                    rightTree = newNode;
                    newNode.setParent(this);

                } else {
                    rightTree.addNode(newNode);
                }
            } else {
                if (leftTree == null) {
                    leftTree = newNode;
                    newNode.setParent(this);

                } else {
                    leftTree.addNode(newNode);

                }
            }
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public void setLeftTree(Node<E> leftTree) {
        this.leftTree = leftTree;
    }

    public void setRightTree(Node<E> rightTree) {
        this.rightTree = rightTree;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node<E> search(E e) {
        if (value.compareTo(e) == 0) {
            return this;
        } else if  (value.compareTo(e) > 0) {
            return leftTree.search(e);
        } else {
            return rightTree.search(e);
        }
    }



    public boolean remove(E e) {
        if (!value.equals(e)) {
            if (e.compareTo(value) < 0) {
                leftTree.remove(e);
            } else {
                rightTree.remove(e);
            }
        } else {

            if (leftTree == null && rightTree != null) {
                if (parent != null) {
                    reconnectParent(rightTree,this, parent);
                } else {
                    rightTree.setParent(this);
                    rightTree.moveUpwards();
                }

            } else if (leftTree != null && rightTree == null) {
                if (parent != null) {
                    reconnectParent(leftTree, this, parent);
                } else {
                    leftTree.setParent(this);
                    leftTree.moveUpwards();
                }
            } else if (leftTree == null && rightTree == null) {
                if (parent != null) {
                    reconnectParent(null, this, parent);
                } else {
                    parent = null;
                }
            } else {

                if (leftTree.depth() >= rightTree.depth()) {

                    reconnectParent(leftTree, this, parent);

                } else {

                    reconnectParent(rightTree, this, parent);

                }
            }
        }
        return true;
    }

    public void reconnectParent(Node<E> newChild, Node<E> currentParent, Node<E> newParent) {
        if (newParent == null) {
            newChild.setParent(null);
        } else if (newChild != null) {

            if (newChild.getValue().compareTo(newParent.getValue()) > 0) {
                newParent.setRightTree(newChild);
            } else {
                newParent.setLeftTree(newChild);
            }

            newChild.setParent(newParent);

        } else {

            if (currentParent.getValue().compareTo(newParent.getValue()) > 0) {
                newParent.setRightTree(null);
            } else {
                newParent.setLeftTree(null);
            }


        }
    }

    //Kijkt na of een node dezelfde waarde heeft als zijn parent, anders treden er duplicates op
    public void checkDuplicate(Node<E> child, Node<E> par) {
        if (par != null) {
            if (par.getRightTree() != null) {
                if (child.getValue().equals(par.getRightTree().getValue())) {
                    par.setRightTree(null);
                }
            } else if (par.getLeftTree() != null) {
                if (child.getValue().equals(par.getLeftTree().getValue())) {
                    par.setLeftTree(null);
                }
            }
        }
    }


    public void moveUpwards() {

        if (leftTree == null && rightTree != null) {
            parent.setValue(value);
            reconnectParent(rightTree, this, parent);
            checkDuplicate(this, parent);

        } else if (leftTree != null && rightTree == null) {
            parent.setValue(value);
            reconnectParent(leftTree, this, parent);
            checkDuplicate(this, parent);

        } else if (leftTree == null && rightTree == null) {


            if (parent.getValue().compareTo(value) > 0) {
                parent.setLeftTree(null);
            } else {
                parent.setRightTree(null);
            }

            parent.setValue(value);



        } else {

            if (leftTree.depth() >= rightTree.depth()) {
                parent.setValue(value);
                reconnectParent(leftTree, this, parent);
                checkDuplicate(this, parent);

            } else {
                parent.setValue(value);
                reconnectParent(rightTree, this, parent);
                checkDuplicate(this, parent);

            }

        }
    }

    public int depth() {
        if (leftTree == null && rightTree == null) {
            return 0;
        } else if (leftTree != null && rightTree == null) {
            return 1 + leftTree.depth();
        } else if (leftTree == null && rightTree != null) {
            return 1 + rightTree.depth();
        } else {
            return Math.max(1 + leftTree.depth(), 1 + rightTree.depth());
        }
    }

    public int distanceToRoot() {
        if (parent == null) {
            return 0;
        } else {
            return 1 + parent.distanceToRoot();
        }
    }

    public Node<E> min() {
        if (leftTree == null) {
            return this;
        } else {
            return leftTree.min();
        }
    }

    public Node<E> max() {
        if (rightTree == null) {
            return this;
        } else {
            return rightTree.max();
        }
    }

    public Node<E> splay(E e) {
        Node<E> start = getRoot().search(e);
        if (start.distanceToRoot() + 1 >= splaySize) {


            Node<E> nextStart = start;
            Node<E> oldRoot = start;
            for (int i = 0; i < splaySize; i++) {
                nextStart = nextStart.getParent();
                if (i + 1 < splaySize) {
                    oldRoot = oldRoot.getParent();
                }
            }


            System.out.println("START VALUE IS " + Integer.toString((Integer)start.getValue()));
            System.out.println("NEXTSTART VALUE IS " + nextStart != null);
            System.out.println("OLROOT VALUE IS " + Integer.toString((Integer)oldRoot.getValue()));

            ArrayList<Node<E>> subTrees = new ArrayList<>();
            Node<E>[] splayPath = getSortedPath(oldRoot, start, subTrees);
            System.out.println("SORTED PATH");
            for (int i = 0; i< splaySize; i++) {
                System.out.println(Integer.toString((Integer)splayPath[i].getValue()));
            }
            Node<E> newRoot = new Node<E>(splayPath[splaySize/2].getValue(), nextStart, splaySize);


            if (nextStart != null) {
                if (newRoot.getValue().compareTo(nextStart.getValue()) < 0) {
                    nextStart.setLeftTree(newRoot);
                } else {
                    nextStart.setRightTree(newRoot);
                }
            }

            addNodesRecursively(splayPath, 0, splaySize/2 - 1, newRoot);
            addNodesRecursively(splayPath, splaySize/2 + 1, splaySize - 1, newRoot);
            System.out.println("TREE AFTER ADDING NODES");
            newRoot.print();


            for (Node<E> node : subTrees) {
                newRoot.addNode(node);
            }

            if (nextStart != null) {
                if (nextStart.distanceToRoot() + 1 >= splaySize) {
                    return nextStart.splay(nextStart.getValue());
                }
            }
            return newRoot;

        }
        return getRoot();
    }

    public Node<E>[] getSortedPath(Node<E> root, Node<E> target, ArrayList<Node<E>> subTrees) {
        Node[] result = new Node[splaySize];
        Node<E> current = root;

        while (current != null) {
            //Huidige node staat rechts van de target-node
            if (current.getValue().compareTo(target.getValue()) > 0) {
                result[rightMostNull(result)] = current;
                subTrees.add(current.getRightTree());
                current = current.getLeftTree();

            } else {
                result[leftMostNull(result)] = current;
                subTrees.add(current.getLeftTree());
                current = current.getRightTree();
            }
        }
        return result;
    }

    /**
     *
     * @param nodes
     * @return index of leftmost null-element
     */
    public int leftMostNull(Node[] nodes) {
        for (int i = 0; i < splaySize; i++) {
            if (nodes[i] == null) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @param nodes
     * @return index of rightmost null-element
     */
    public int rightMostNull(Node[] nodes) {
        for (int i = splaySize - 1; i >= 0; i--) {
            if (nodes[i] == null) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @param splayPath
     * Adds nodes in a certain order so that the new binary tree will be as balanced as possible
     */
    public void addNodesRecursively(Node<E>[] splayPath, int start, int end, Node<E> currentTree) {
        System.out.println("RECURSIVE CALL WITH START = " + Integer.toString(start) + " AND END = " + Integer.toString(end));
        int len = end - start;

        if (len == 0) {
            currentTree.add(splayPath[start + (len / 2)].getValue());

        } else if (len > 0) {
            currentTree.add(splayPath[start + len / 2].getValue());
            addNodesRecursively(splayPath, start, start + len / 2 - 1, currentTree);
            addNodesRecursively(splayPath, start + len / 2 + 1, end, currentTree);
        }

    }

    public void print() {
        String left = "";
        String right = "";

        if (leftTree != null) {
            System.out.println("Linkerkind van " + value.toString() + " is " + leftTree.getValue().toString());
            leftTree.print();
        }

        if (rightTree != null) {
            System.out.println("Rechterkind van " + value.toString() + " is " + rightTree.getValue().toString());
            rightTree.print();
        }
    }

}
