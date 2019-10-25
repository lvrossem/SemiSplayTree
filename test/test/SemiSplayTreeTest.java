package test;

import org.junit.jupiter.api.Test;
import semisplay.SemiSplayTree;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class SemiSplayTreeTest {

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

    @Test
    public void treeMinMaxTest() {
        SemiSplayTree<String> tree = new SemiSplayTree<>(3);
        assertEquals("Must be null",null, tree.getTree().max().getValue());
        assertEquals("Must be null",null, tree.getTree().min().getValue());
        tree.add("Mike");
        tree.add("Sierra");
        tree.add("Golf");
        assertEquals("Must be Sierra","Sierra", tree.getTree().max().getValue());
        assertEquals("Must be Golf","Golf", tree.getTree().min().getValue());
        tree.add("Kilo");
        tree.add("Lima");
        tree.add("Quebec");
        tree.add("Oscar");
        assertEquals("Must be Sierra","Sierra", tree.getTree().max().getValue());
        assertEquals("Must be Golf","Golf", tree.getTree().min().getValue());
        tree.add("Alfa");
        tree.add("Zulu");
        assertEquals("Must be Zulu","Zulu", tree.getTree().max().getValue());
        assertEquals("Must be Alfa","Alfa", tree.getTree().min().getValue());
        tree.add("Bravo");
        tree.add("Charlie");
        tree.add("Delta");
        tree.add("Echo");
        tree.add("Foxtrot");
        tree.add("Hotel");
        tree.add("India");
        tree.add("Juliet");
        tree.add("November");
        tree.add("Papa");
        tree.add("Romeo");
        tree.add("Tango");
        tree.add("Uniform");
        tree.add("Victor");
        tree.add("Whiskey");
        tree.add("X-ray");
        tree.add("Yankee");
        assertEquals("Must be Zulu","Zulu", tree.getTree().max().getValue());
        assertEquals("Must be Alfa","Alfa", tree.getTree().min().getValue());
        tree.remove("Zulu");
        tree.remove("Alfa");
        assertEquals("Must be Yankee","Yankee", tree.getTree().max().getValue());
        assertEquals("Must be Bravo","Bravo", tree.getTree().min().getValue());


    }

    @Test
    public void iteratorTest() {
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
        assertEquals("Must be 1", 1, result);

        for (int i = 0; i < 4000; i++) {
            int n = RG.nextInt(10000);
            tree.remove(n);
            System.out.println(n);
        }


        list.clear();
        for (Integer i: tree) {
            list.add(i);
        }

        result = 0;
        if (isSorted(list) && !containsDuplicates(list)) {
            result = 1;
        }


        assertEquals("Must be 1", 1, result);




    }

    public boolean isSorted(ArrayList<Integer> list)
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

    public boolean containsDuplicates(ArrayList<Integer> list) {
        boolean duplicates = false;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i+1))) {
                duplicates = true;
            }
        }
        return duplicates;
    }
}