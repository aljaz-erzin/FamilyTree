import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void countParentsTest() throws Exception
    {
        int[][] child_parent = {{1, 0},
                {2, 1},
                {3, 0},
                {4, 0},
                {5, 1},
                {6, 1},
                {7, 3},
                {8, 3},
                {10, 9},
                {2, 10},
                {5, 10},
                {11, 10},
                {12, 10},
                {14, 13},
                {15, 5}};
        int[] correctResult = {0, 1, 2, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1};
        int[] resultOfAMethod = App.countParents(child_parent, child_parent.length);
        assertArrayEquals(correctResult, resultOfAMethod);
    }

    @Test
    void makePQTest()  throws Exception
    {
        int[] numOfParents = {0, 1, 2, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1};
        PriorityQueue<Person> methodResult = new PriorityQueue<Person>(numOfParents.length, new SortingAlgorithm());

        methodResult = App.makePQ( methodResult,  numOfParents);

        assertEquals(methodResult.poll().name, 0);
        assertEquals(methodResult.poll().name, 9);
        assertEquals(methodResult.poll().name, 13);
        assertEquals(methodResult.poll().name, 14);
        assertEquals(methodResult.poll().name, 3);
        assertEquals(methodResult.poll().name, 10);
        assertEquals(methodResult.poll().name, 4);
        assertEquals(methodResult.poll().name, 7);
        assertEquals(methodResult.poll().name, 8);
        assertEquals(methodResult.poll().name, 6);
        assertEquals(methodResult.poll().name, 12);
        assertEquals(methodResult.poll().name, 1);
        assertEquals(methodResult.poll().name, 11);
        assertEquals(methodResult.poll().name, 5);
        assertEquals(methodResult.poll().name, 2);

    }

    @Test
    void child_parent_cycleTest()  throws Exception
    {
        LinkedList<Integer>[] listsOfParents = new LinkedList[2];
        listsOfParents[0] = new LinkedList<Integer>();
        listsOfParents[1] = new LinkedList<Integer>();
        listsOfParents[0].add(1);
        listsOfParents[1].add(0);
        String[] names = {"Aljaž", "Urša"};

        try {
            App.child_parent_cycle(listsOfParents, 1, 0, names);
            fail("Here should be a cycle betwen Aljaž and Urša!");

        } catch (IllegalArgumentException e) {
            // All is good, function recognizes cycles!
        }
    }

    @Test
    void mainFunctionTest() throws Exception
    {
        FileReader inputFile = new FileReader("test_data.txt");
        BufferedReader bufferReader = new BufferedReader(inputFile);
        Scanner sc = new Scanner(new File("test_data.txt"));

        String[][] child_parent_String = new String[0][2];
        child_parent_String = App.readInput(sc, child_parent_String);

        String[] names = new String[0];
        names = App.rememberNames(child_parent_String, names);

        int[][] child_parent = App.convertToNumbers(child_parent_String, names);

        int[] numOfParents = App.countParents(child_parent, names.length);

        ResultTree[] resultOfMainMethod = App.initResultTree(numOfParents);

        PriorityQueue<Person> pq = new PriorityQueue<Person>(names.length, new SortingAlgorithm());
        pq = App.makePQ(pq, numOfParents);

        LinkedList<Integer>[] listsOfParents = App.initLinkedList(names.length);
        resultOfMainMethod = App.mainFunction(names, pq, child_parent, listsOfParents, resultOfMainMethod);

        ResultTree[] correctResult = new ResultTree[2];
        correctResult[0] = new ResultTree(0);
        correctResult[1] = new ResultTree(2);

        ResultTree tmp = correctResult[0];

        tmp.child = new ResultTree(1);
        tmp.child.brother = new ResultTree(7);
        tmp.child.brother.child = new ResultTree(8);
        tmp.child.brother.child.brother = new ResultTree(9);
        tmp.child.brother.child.brother.brother = new ResultTree(10);
        tmp.child.child = new ResultTree(5);
        tmp.child.child.brother = new ResultTree(6);
        tmp = correctResult[1];
        tmp.child = new ResultTree(3);
        tmp.child.brother = new ResultTree(4);
        tmp.child.child = new ResultTree(5);
        tmp.child.child.brother = new ResultTree(6);
        tmp.child.brother.child = new ResultTree(11);
        tmp.child.brother.child.brother = new ResultTree(12);

        for (int i = 0; i < correctResult.length; i++)
        {
            boolean flag = correctResult[i].checkIfTheSame(resultOfMainMethod[i]);
            if (!flag)
            {
                throw new Exception("Result tree that mainFunction builds doesn't match the correct tree!");
            }
        }
    }

}