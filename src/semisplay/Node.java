package semisplay;

import java.util.ArrayList;

public class Node<E extends Comparable<E>> {

    private Node<E> parent;
    private Node<E> leftTree;
    private Node<E> rightTree;

    private E value;

    public Node(E value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public E getValue() {
        return value;
    }

    public Node getLeftTree() {
        return leftTree;
    }

    public Node getRightTree() {
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
        if (value == null) {
            return false;
        } else if (value.compareTo(e) == 0) {
            return true;
        } else if (value.compareTo(e) > 0 && leftTree != null) {
            return leftTree.contains(e);
        } else if (value.compareTo(e) < 0 && rightTree != null) {
            return rightTree.contains(e);
        } else {
            return false;
        }
    }

    public boolean add(E e) {
        if (value == null) {
            value = e;
            return true;
        } else if (e.compareTo(value) < 0) {
            if (leftTree == null) {
                leftTree = new Node(e, this);
                return true;
            } else {
                return leftTree.add(e);
            }
        } else {
            if (rightTree == null) {
                rightTree = new Node(e, this);
                return true;
            } else {
                return rightTree.add(e);
            }
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setLeftTree(Node leftTree) {
        this.leftTree = leftTree;
    }

    public void setRightTree(Node rightTree) {
        this.rightTree = rightTree;
    }

    public void setValue(E value) {
        this.value = value;
    }



    public boolean remove(E e) {
        if (value != e) {
            if (e.compareTo(value) < 0) {
                if (leftTree != null) {
                    leftTree.remove(e);
                } else if (rightTree != null) {
                    rightTree.remove(e);
                }
            } else {

                if (rightTree != null) {
                    rightTree.remove(e);
                } else if (leftTree != null) {
                    leftTree.remove(e);
                }
            }
        } else {

            if (leftTree == null && rightTree != null) {
                reconnectParent(rightTree);

            } else if (leftTree != null && rightTree == null) {
                reconnectParent(leftTree);
            } else if (leftTree == null && rightTree == null) {
                if (parent != null) {
                    reconnectParent(null);
                }
            } else {

                if (leftTree.depth() >= rightTree.depth()) {

                    replaceDeleted(leftTree.max());

                } else {
                    replaceDeleted(rightTree.min());
                }
            }
        }
        return true;
    }

    public void reconnectParent(Node<E> newChild) {
        if (value.compareTo(parent.getValue()) > 0) {
            parent.setRightTree(newChild);
        } else {
            parent.setLeftTree(newChild);
        }
    }

    public void replaceDeleted(Node<E> node) {
        value = node.getValue();

        if (node.getRightTree() != null) {
            node.getRightTree().moveUpwards();
        } else if (node.getLeftTree() != null){
            node.getLeftTree().moveUpwards();
        } else {
            if (node.getValue().compareTo((E) node.getParent().getValue()) > 0) {
                node.getParent().setRightTree(null);
            } else {
                node.getParent().setLeftTree(null);
            }
        }
    }


    public void moveUpwards() {



        if (leftTree == null && rightTree != null) {
            getParent().setValue(value);
            value = null;
            rightTree.moveUpwards();
        } else if (leftTree != null && rightTree == null) {
            getParent().setValue(value);
            value = null;
            leftTree.moveUpwards();
        } else if (leftTree == null && rightTree == null) {
            if (parent != null) {
                getParent().setValue(value);
                if (value.compareTo(parent.getValue()) > 0) {
                    parent.setRightTree(null);
                } else {
                    parent.setLeftTree(null);
                }

            }
        } else {

            if (leftTree.depth() >= rightTree.depth()) {
                getParent().setValue(value);
                value = null;
                leftTree.moveUpwards();

            } else {
                getParent().setValue(value);
                value = null;
                rightTree.moveUpwards();
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

    public void print() {
        String left = "";
        String right = "";
        System.out.println(value);
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
