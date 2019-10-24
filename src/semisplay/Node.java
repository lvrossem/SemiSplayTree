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
        } else if (value.equals(e)) {
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

    public void setParent(Node<E> parent) {
        this.parent = parent;
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
        if (!value.equals(e)) {
            if (e.compareTo(value) < 0) {
                System.out.println(value);
                leftTree.remove(e);
            } else {
                System.out.println(value);
                rightTree.remove(e);
            }
        } else {

            if (leftTree == null && rightTree != null) {
                if (parent != null) {
                    reconnectParent(rightTree,this, parent);
                }

            } else if (leftTree != null && rightTree == null) {
                if (parent != null) {
                    reconnectParent(leftTree, this, parent);
                }
            } else if (leftTree == null && rightTree == null) {
                if (parent != null) {
                    reconnectParent(null, this, parent);
                }
            } else {

                if (leftTree.depth() >= rightTree.depth()) {

                    E temp = value;
                    value = leftTree.max().getValue();
                    leftTree.max().setValue(temp);
                    leftTree.remove(temp);

                } else {

                    E temp = value;
                    value = rightTree.min().getValue();
                    rightTree.min().setValue(temp);
                    rightTree.remove(temp);
                }
            }
        }
        return true;
    }

    public void reconnectParent(Node<E> newChild, Node<E> currentParent, Node<E> newParent) {
        if (newChild != null) {
            if (newChild.getValue().compareTo(newParent.getValue()) > 0) {
                newParent.setRightTree(newChild);
            } else {
                newParent.setLeftTree(newChild);
            }
            newChild.setParent(newParent);
        } else {
            if (currentParent.getValue().compareTo(newParent.getValue()) > 0) {
                newParent.setRightTree(newChild);
            } else {
                newParent.setLeftTree(newChild);
            }
        }
    }


    public void replaceDeleted(Node<E> node) {
        value = node.getValue();

        if (node.getRightTree() != null) {
            node.getRightTree().moveUpwards();
        } else if (node.getLeftTree() != null){
            node.getLeftTree().moveUpwards();
        } else {
            reconnectParent(null, node, node.getParent());
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
