package test;

import org.junit.jupiter.api.Test;
import semisplay.SemiSplayTree;

import static org.junit.Assert.*;

public class SemiSplayTreeSizeTest {

    @Test
    public void treeDepthTest() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        assertEquals("Must be 0",0, tree.depth());
        tree.add(20);
        assertEquals("Must be 0",0, tree.depth());
        tree.add(10);
        assertEquals("Must be 1",1, tree.depth());
        tree.add(30);
        assertEquals("Must be 1",1, tree.depth());
        tree.add(31);
        assertEquals("Must be 2",2, tree.depth());
        tree.add(32);
        assertEquals("Must be 3",3, tree.depth());
        tree.add(33);
        assertEquals("Must be 4",4, tree.depth());
        tree.add(34);
        assertEquals("Must be 5",5, tree.depth());
        tree.add(35);
        assertEquals("Must be 6",6, tree.depth());



    }
}