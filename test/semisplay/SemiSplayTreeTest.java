package semisplay;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
//Testklasse om de functies van de semisplaytree te testen, tot nu toe werkt iedere test
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
    public void treeMinMaxContainTest() {
        SemiSplayTree<String> tree = new SemiSplayTree<>(3);
        assertTrue(!tree.contains("Mike"));
        assertEquals("Must be null",null, tree.getTree().max().getValue());
        assertEquals("Must be null",null, tree.getTree().min().getValue());
        tree.add("Mike");
        tree.add("Sierra");
        assertTrue(tree.contains("Mike"));
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
        assertTrue(tree.contains("Foxtrot"));
        tree.add("Hotel");
        tree.add("India");
        tree.add("Juliet");
        tree.add("November");
        tree.add("Papa");
        tree.add("Romeo");
        tree.add("Tango");
        tree.add("Uniform");
        assertTrue(tree.contains("Foxtrot"));
        assertTrue(!tree.contains("Victor"));
        tree.add("Victor");
        assertTrue(tree.contains("Foxtrot"));
        tree.remove("Victor");
        assertTrue(!tree.contains("Victor"));
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
        for (int i = 0; i < 100000; i++) {
            tree.add(RG.nextInt(10000));
        }

        for (Integer i: tree) {
            list.add(i);
        }


        //KIjkt na of de boom nog steeds klopt
        int result = 0;
        if (isSorted(list) && list.size() > 1 && !containsDuplicates(list)) {
            result = 1;
        }
        assertTrue(isSorted(list) && list.size() > 1 && !containsDuplicates(list));

        //Haal alle elementen uit de boom om te testen of remove werkt
        for (int i: tree) {
            tree.remove(i);
        }




        list.clear();
        for (Integer i: tree) {
            list.add(i);
        }

        result = 0;
        if (isSorted(list) && !containsDuplicates(list)) {
            result = 1;
        }


        assertTrue(isSorted(list) && !containsDuplicates(list));




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