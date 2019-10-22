package semisplay;

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
        if (getRightTree() == null && getLeftTree() == null) {
            return 1;
        } else if (getRightTree() == null && getLeftTree() != null) {
            return getLeftTree().size();
        } else if (getRightTree() != null && getLeftTree() == null) {
            return getRightTree().size();
        } else {
            return getLeftTree().size() + getRightTree().size();
        }
    }

    public boolean contains(E e) {
        if (value == e) {
            return true;
        } else {
            if (getRightTree() == null && getLeftTree() == null) {
                return false;
            } else if (getRightTree() == null && getLeftTree() != null) {
                return getLeftTree().contains(e);
            } else if (getRightTree() != null && getLeftTree() == null) {
                return getRightTree().contains(e);
            } else {
                return getLeftTree().contains(e) || getRightTree().contains(e);
            }
        }
    }

    public boolean add(E e) {
        if (value == null) {
            value = e;
            return true;
        } else if (e.compareTo(value) > 0) {
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

    public void setParent(Node parent) {
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

    public void moveUowards() {
        getParent().setValue(value);
        value = null;

        if (leftTree == null && rightTree != null) {
            rightTree.moveUowards();
        } else if (leftTree != null && rightTree == null) {
            leftTree.moveUowards();
        } else if (leftTree == null && rightTree == null) {
            if (parent != null) {
                if (value.compareTo(parent.getValue()) > 0) {
                    parent.setRightTree(null);
                } else {
                    parent.setLeftTree(null);
                }
            }
        } else {

            if (leftTree.depth() >= rightTree.depth()) {
                leftTree.moveUowards();
            } else {
                rightTree.moveUowards();
            }
        }
    }

    public boolean remove(E e) {
        if (value != e) {
            if (e.compareTo(value) < 0) {
                leftTree.remove(e);
            } else {
                rightTree.remove(e);
            }
        } else {
            value = null;
            if (leftTree == null && rightTree != null) {
                rightTree.moveUowards();
            } else if (leftTree != null && rightTree == null) {
                leftTree.moveUowards();
            } else if (leftTree == null && rightTree == null) {
                if (parent != null) {
                    if (value.compareTo(parent.getValue()) > 0) {
                        parent.setRightTree(null);
                    } else {
                        parent.setLeftTree(null);
                    }
                }
            } else {

                if (leftTree.depth() >= rightTree.depth()) {
                    leftTree.moveUowards();
                } else {
                    rightTree.moveUowards();
                }
            }
        }
        return true;
    }

    public int depth() {
        if (leftTree == null && rightTree == null) {
            return 0;
        } else if (leftTree != null && rightTree == null) {
            return leftTree.depth();
        } else if (leftTree == null && rightTree != null) {
            return rightTree.depth();
        } else {
            return Math.max(1 + leftTree.depth(), 1 + rightTree.depth());
        }
    }

    public void print() {
        System.out.println(value);
        if (leftTree != null) {
            leftTree.print();
        }

        if (rightTree != null) {
            rightTree.print();
        }
    }

}
