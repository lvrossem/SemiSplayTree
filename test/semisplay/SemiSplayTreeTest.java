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
        tree.getTree().print();
        assertEquals("Must be 0",0, tree.depth());
        tree.add(10);
        assertEquals("Must be 1",1, tree.depth());
        tree.add(30);
        tree.getTree().print();
        assertEquals("Must be 1",1, tree.depth());
        tree.add(31);
        assertEquals("Must be 2",2, tree.depth());
        tree.add(32);
        tree.getTree().print();
        assertEquals("Must be 3",3, tree.depth());
        tree.add(33);
        tree.getTree().print();
        assertEquals("Must be 4",4, tree.depth());
        tree.add(34);
        assertEquals("Must be 5",5, tree.depth());
        tree.add(35);
        assertEquals("Must be 6",6, tree.depth());

        tree = new SemiSplayTree<>(4);
        tree.add(8);
        tree.add(4);
        tree.add(12);
        tree.add(2);
        assertEquals("Must be 2",2, tree.depth());
        tree.add(6);
        tree.add(10);
        tree.add(14);
        tree.add(1000);
        /*
        System.out.println("ADDED 1000");
        tree.getTree().print();
        */

        assertEquals("Must be 3",3, tree.depth());
        tree.add(500);
        tree.add(1500);
        assertEquals("Must be 3",3, tree.depth());
        tree.add(2000);
        assertEquals("Must be 4",4, tree.depth());



    }

    @Test
    public void treeMinMaxContainTest() {
        //Hier gebruiken we Strings ipv ints om te testen om na te kijken of het met anere datatypes ook werkt
        SemiSplayTree<String> tree = new SemiSplayTree<>(3);
        assertTrue(!tree.contains("Mike"));
        assertEquals("Must be null",null, tree.getTree().max().getValue());
        assertEquals("Must be null",null, tree.getTree().min().getValue());

        tree.add("Mike");

        tree.add("November");

        tree.add("Oscar");

        tree.add("Papa");


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
        //Dit werd ook al getest voor veel andere splaygroottes dan 25
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(25);
        Random RG = new Random(50);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int added = RG.nextInt(1000);
            tree.add(added);
            assertTrue(tree.contains(added));

        }

        for (Integer i: tree) {
            list.add(i);
        }



        assertTrue(isSorted(list) && list.size() > 1 && !containsDuplicates(list));

        //Haal alle elementen uit de boom om te testen of remove werkt
        for (int i = 0; i < 100; i++) {
            int removed = RG.nextInt(1000);
            tree.remove(removed);
            assertTrue(!tree.contains(removed));
        }




        list.clear();
        for (Integer i: tree) {
            list.add(i);
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