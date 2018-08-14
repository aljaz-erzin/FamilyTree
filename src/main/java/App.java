import java.util.*;
import java.io.*;

public class App
{

    public static String[][] expend2DArray(String[][] oldArray)
    {

        String[][] newArray = new String[oldArray.length+1][2];

        for (int i = 0; i < oldArray.length; i++)
        {
            newArray[i][0] = oldArray[i][0];
            newArray[i][1] = oldArray[i][1];
        }

        return newArray;
    }

    public static String[] expend1DArray(String[] oldArray)
    {

        String[] newArray = new String[oldArray.length+1];

        for (int i = 0; i < oldArray.length; i++)
        {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }

    public static boolean checkIfExists(String name, String[] savedNames)
    {
        for (int i = 0; i < savedNames.length; i++)
        {
            if (savedNames[i].equals(name))
            {
                return true;
            }
        }
        return false;
    }

    public static String[][] readInput(Scanner sc, String[][] array)
    {
        while (sc.hasNext())
        {
            array = expend2DArray(array);
            String child = sc.next();
            String parent = sc.next();
            array[array.length-1][0] = child;
            array[array.length-1][1] = parent;
        }
        return array;
    }

    public static String[] rememberNames(String[][] child_parent, String[] names)
    {
        int i = 0;
        while (i < child_parent.length)
        {
            String child = child_parent[i][0];
            String parent = child_parent[i][1];
            if (!checkIfExists(parent, names))
            {
                names = expend1DArray(names);
                names[names.length-1] = parent;
            }
            if (!checkIfExists(child, names))
            {
                names = expend1DArray(names);
                names[names.length-1] = child;
            }
            i++;
        }
        return names;
    }

    public static int[][] convertToNumbers(String[][] child_parent, String[] names)
    {
        // Method converts names to numbers
        int[][] array = new int[child_parent.length][2];
        for (int i = 0; i < names.length; i++)
        {
            String name = names[i];
            for (int j = 0; j < child_parent.length; j++)
            {
                if (name.equals(child_parent[j][0]))
                {
                    array[j][0] = i;
                }
                else if (name.equals(child_parent[j][1]))
                {
                    array[j][1] = i;
                }
            }
        }
        return array;
    }

    public static int[] countParents(int[][] child_parent, int len)
    {
        //Method count how many parents does each child have
        int[] array = new int[len];
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < child_parent.length; j++)
            {
                if (child_parent[j][0] == i)
                {
                    array[i]++;
                }
            }
        }
        return array;
    }

    public static PriorityQueue<Person> makePQ(PriorityQueue<Person> pq, int[] numOfParents)
    {
        // Children with less parents will be first
        for (int i = 0; i < numOfParents.length; i++)
        {
            Person newPerson = new Person(i, numOfParents[i]);
            pq.add(newPerson);
        }
        return pq;
    }

    public static LinkedList<Integer>[] initLinkedList(int len)
    {
        LinkedList<Integer>[] list =  new LinkedList[len];
        for (int i = 0; i < list.length; i++)
        {
            list[i] = new LinkedList<Integer>();
        }
        return list;
    }

    public static String findFirstParents(int[] numOfParents)
    {
        String firstParents = "";
        for (int i = 0; i < numOfParents.length; i++)
        {
            if (numOfParents[i] == 0)
            {
                firstParents += (i + " ");
            }
        }
        return firstParents;
    }

    public static int[] convertToArray(String firstParents_string)
    {
        String[] splitFirstParents = firstParents_string.split(" ");
        int[] firstParents = new int[splitFirstParents.length];
        for (int i = 0; i < splitFirstParents.length; i++)
        {
            firstParents[i] = Integer.parseInt(splitFirstParents[i]);
        }
        return firstParents;
    }

    public static ResultTree[] initResultTree(int[] numOfParents)
    {
        String firstParents_string = findFirstParents(numOfParents);
        int[] firstParents = convertToArray(firstParents_string);
        ResultTree[] result = new ResultTree[firstParents.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = new ResultTree(firstParents[i]);
        }
        return result;
    }

    public static void child_parent_cycle(LinkedList<Integer>[] listsOfParents, int tmpChild, int tmpParent, String[] names) throws Exception
    {
        // Method checks  for a cycles!
        Integer tmpInt = tmpChild;
        for (int i = 0; i < listsOfParents[tmpParent].size(); i++)
        {
                if (listsOfParents[tmpParent].get(i) == tmpChild)
                {
                    throw new IllegalArgumentException(names[tmpChild]+ " and " + names[tmpParent] + " can not be both children and parents for one another!");

                }
        }
    }

    public static ResultTree[] mainFunction(String[] names, PriorityQueue<Person> pq, int[][] child_parent, LinkedList<Integer>[] listsOfParents, ResultTree[] result) throws Exception
    {
        while (!pq.isEmpty())
        {
            Person tmpPerson = pq.poll();
            for (int i = 0; i < child_parent.length; i++)
            {

                int tmpParent = child_parent[i][1];
                if (tmpParent == tmpPerson.name)
                {
                    int tmpChild = child_parent[i][0];

                    child_parent_cycle(listsOfParents, tmpChild, tmpParent, names);

                    listsOfParents[tmpChild] = (LinkedList) listsOfParents[tmpParent].clone();
                    listsOfParents[tmpChild].add(tmpParent);
                    for (int j = 0; j < result.length; j++)
                    {
                        ResultTree tmpElTree = result[j];

                        if (tmpElTree.name == listsOfParents[tmpChild].getFirst())
                        {

                            ResultTree newNode = new ResultTree(tmpChild);
                            if (tmpElTree.child  == null )
                            {
                                tmpElTree.child = newNode;
                            }
                            else
                            {

                                tmpElTree = tmpElTree.child;
                                boolean flag = true;
                                for (int k = 1; k < listsOfParents[tmpChild].size(); k++)
                                {
                                    flag = false;
                                    int tmpElList = listsOfParents[tmpChild].get(k);
                                    boolean flag1 = true;

                                    while (tmpElList != tmpElTree.name)
                                    {
                                        tmpElTree = tmpElTree.brother;
                                    }

                                    if (tmpElTree.child  == null)
                                    {
                                        tmpElTree.child = newNode;
                                        break;
                                    }
                                    else if (k == (listsOfParents[tmpChild].size()-1))
                                    {

                                        tmpElTree = tmpElTree.child;
                                        while (tmpElTree.brother != null)
                                        {
                                            tmpElTree = tmpElTree.brother;
                                        }
                                        tmpElTree.brother = newNode;
                                    }
                                    tmpElTree = tmpElTree.child;
                                }
                                if (flag)
                                {
                                    while (tmpElTree.brother != null)
                                    {
                                        tmpElTree = tmpElTree.brother;
                                    }
                                    tmpElTree.brother = newNode;
                                }
                            }

                            break;
                        }
                    }
                   // System.out.print(listsOfParents[tmpChild]+ " child " + " " + tmpChild);
                  //  System.out.println(listsOfParents[tmpParent]+ " father " + " " + tmpParent);
                    Iterator<Person>  iterator = pq.iterator();
                    while (iterator.hasNext()) {
                        Person p = iterator.next();
                        if (p.name == tmpChild)
                        {
                            Person newPerson = p;
                            iterator.remove();
                            newPerson.numOfParents--;
                            pq.add(newPerson);
                            break;
                        }
                    }

                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception
    {

        FileReader inputFile = new FileReader("data.txt");
        BufferedReader bufferReader = new BufferedReader(inputFile);
        Scanner sc = new Scanner(new File("data.txt"));

        String[][] child_parent_String = new String[0][2];
        child_parent_String = readInput(sc, child_parent_String);

        String[] names = new String[0];
        names = rememberNames(child_parent_String, names);

        //Convert names to numbers for easier indexing
        int[][] child_parent = convertToNumbers(child_parent_String, names);
        // child_parent[0] = {1, 0}, before child_parent[0] = {"Adam", "Ivan"};

        int[] numOfParents = countParents(child_parent, names.length);

        ResultTree[] result = initResultTree(numOfParents);

        PriorityQueue<Person> pq = new PriorityQueue<Person>(names.length, new SortingAlgorithm());
        pq = makePQ(pq, numOfParents);

        LinkedList<Integer>[] listsOfParents = initLinkedList(names.length); // Lists for all nodes

        result = mainFunction(names, pq, child_parent, listsOfParents, result);
        // Element in result array is a tree -> result[0] = {Ivan, 0} -> {Adam, 1}...

        for (int i = 0; i < result.length; i++)
        {
            result[i].print(names);
        }
    }




}