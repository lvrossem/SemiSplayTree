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

    /**
     * Voegt een top, inclusief zijn kind-bomen, toe aan een nieuwe boom
     * @param newNode
     */
    public void addNode(Node<E> newNode) {

        if (newNode != null) {
            if (newNode.getValue().compareTo(value) > 0) {
                if (rightTree == null) {
                    rightTree = newNode;
                    newNode.setParent(this);
                } else {
                    rightTree.addNode(newNode);
                }
            } else if (newNode.getValue().compareTo(value) < 0) {
                if (leftTree == null) {
                    leftTree = newNode;
                    newNode.setParent(this);
                } else {
                    leftTree.addNode(newNode);

                }
            }
        }
    }

    public Node<E> getParent() {
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

    /**
     * Zoekt een top aan de hand van een waarde zonder te splayen
     * @param e
     * @return Top met opgegeven waarde
     */
    public Node<E> getNodeByValue(E e) {
        if (value.equals(e)) {
            return this;
        } else if (value.compareTo(e) > 0) {
            return leftTree.getNodeByValue(e);
        } else {
            /*
            System.out.println(e);
            print();
            */

            return rightTree.getNodeByValue(e);

        }
    }

    /**
     * Geeft top met gegeven waarde en splayt vanaf die top
     * @param e
     * @return Top met gegeven waarde
     */
    public Node<E> search(E e) {
        if (value.equals(e)) {
            return this;
        } else if (value.compareTo(e) > 0) {
            return leftTree.getNodeByValue(e);
        } else {
            /*
            System.out.println(e);
            print();
            */
            return rightTree.getNodeByValue(e);
        }
    }


    /**
     * Verwijdert een element uit de boom
     * @param e
     * @return true als het element verwijderd is, anders false
     */
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
                    leftTree.addNode(rightTree);
                } else {
                    reconnectParent(rightTree, this, parent);
                    rightTree.addNode(leftTree);
                }
            }
        }
        return true;
    }

    /**
     *
     * @param newChild: topdie nieuwe parent moet krijgen
     * @param currentParent: huidige parent van newChild
     * @param newParent: nieuwe parent voor newChild
     */
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

    /**
     * Beweegt de boom onder het verwijderde element naar omhoog
     */
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

    /**
     *
     * @return Diepte van de boom ten opzichte van deze top
     */
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

    /**
     *
     * @return Het aantal stappen van een top naar de wortel
     */
    public int distanceToRoot() {
        if (parent == null) {
            return 0;
        } else {
            return 1 + parent.distanceToRoot();
        }
    }

    /**
     *
     * @return kleinste element van de boom
     */
    public Node<E> min() {
        if (leftTree == null) {
            return this;
        } else {
            return leftTree.min();
        }
    }

    /**
     *
     * @return grootste element van de boom
     */
    public Node<E> max() {
        if (rightTree == null) {
            return this;
        } else {
            return rightTree.max();
        }
    }

    /**
     *
     * @param e
     * @return de laatse top op het pad naar de locatie waar de top met waarde e zou moeten komen, nodig als we zoeken naar een top die nog niet bestaat
     */
    public Node<E> getDestination(E e) {
        if (value.compareTo(e) > 0) {
            if (leftTree == null) {
                return this;
            } else {
                return leftTree.getDestination(e);
            }
        } else {
            if (rightTree == null) {
                return this;
            } else {
                return rightTree.getDestination(e);
            }
        }
    }

    /**
     *
     * @param e
     * @return Geeft de wortel van de nieuwe boom die gemaakt is in de splaystap
     */
    public Node<E> splay(E e) {
        Node<E> start = getRoot().getNodeByValue(e);

        if (start.distanceToRoot() + 1 >= splaySize) {

            //Top staat diep genoeg om te kunen splayen
            Node<E> oldRoot = start;
            for (int i = 0; i < splaySize - 1; i++) {
                oldRoot = oldRoot.getParent();
            }

            Node<E> oldRootParent = oldRoot.getParent();

            ArrayList<Node<E>> subTrees = new ArrayList<>();
            Node<E>[] splayPath = getSortedPath(oldRoot, start, subTrees);

            Node<E> newRoot = new Node<E>(splayPath[splaySize/2].getValue(), null, splaySize);

            addNodesRecursively(splayPath, 0, splaySize/2 - 1, newRoot);
            addNodesRecursively(splayPath, splaySize/2 + 1, splaySize - 1, newRoot);

            for (Node<E> node : subTrees) {
                newRoot.addNode(node);
            }

            newRoot.setParent(oldRootParent);
            if (oldRootParent != null) {
                if (newRoot.getValue().compareTo(oldRootParent.getValue()) < 0) {
                    oldRootParent.setLeftTree(newRoot);
                } else {
                    oldRootParent.setRightTree(newRoot);
                }
            }

            return newRoot.splay(newRoot.getValue());

        }
        return start.getRoot();
    }

    /**
     *
     * @param root: De node waarvan we vertrekken
     * @param target: De node waar we naartoe werken
     * @param subTrees: ient om een lijst van subtrees te maken die we later zullen toevoegen
     * @return een array waarin de toppen van het splaypad gesorteerd staan
     */
    public Node<E>[] getSortedPath(Node<E> root, Node<E> target, ArrayList<Node<E>> subTrees) {
        Node[] result = new Node[splaySize];
        Node<E> current = root;
        int steps = 1;
        while (steps <= splaySize) {
            //Huidige node staat rechts van de target-node
            if (current.getValue().compareTo(target.getValue()) > 0) {
                result[rightMostNull(result)] = current;

                if (steps == splaySize) {
                    subTrees.add(current.getRightTree());
                    subTrees.add(current.getLeftTree());
                } else {
                    subTrees.add(current.getRightTree());

                }
                current = current.getLeftTree();

            } else {
                result[leftMostNull(result)] = current;

                if (steps == splaySize) {
                    subTrees.add(current.getRightTree());
                    subTrees.add(current.getLeftTree());
                } else {
                    subTrees.add(current.getLeftTree());

                }
                current = current.getRightTree();
            }
            steps++;
        }
        return result;
    }

    /**
     *
     * @param nodes
     * @return index van het meest linkse null-element
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
     * @return index van het meest rechtse null-element
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
     * Voegt alle toppen op het pad toe aan een nieuwe boom in een bepaalde volgorde zodat de nieuwe boom zo gebalanceerd mogelijk is
     */
    public void addNodesRecursively(Node<E>[] splayPath, int start, int end, Node<E> currentTree) {
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
