import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void countParents() throws Exception
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
    void makePQ()  throws Exception
    {

    }

    @Test
    void child_parent_cycle()  throws Exception
    {
        LinkedList<Person>[] listsOfParents = new LinkedList[2];
        listsOfParents[0] = new LinkedList<Person>();
    }

    @Test
    void mainFunction()  throws Exception
    {

    }

}