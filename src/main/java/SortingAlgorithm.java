import java.util.*;
public class SortingAlgorithm implements Comparator<Person>
{
    public int compare(Person one, Person two) {
        if (one.numOfParents > two.numOfParents)
            return 1;
        else if (one.numOfParents < two.numOfParents)
            return -1;
        return 0;

    }
}
