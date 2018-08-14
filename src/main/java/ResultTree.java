public class ResultTree
{
    public  int name;
    public  ResultTree brother;
    public ResultTree child;
    public ResultTree(int name)
    {
        this.name = name;
        this.brother = null;
        this.child = null;
    }

    public void printRecursive(ResultTree current, int depth, String[] names)
    {

        for (int i = 0; i < depth; i++)
        {
            System.out.print("      ");
        }
        System.out.println(names[current.name]);
        if (current.child != null)
        {
            printRecursive(current.child, depth+1, names);
        }
        if (current.brother != null)
        {
            printRecursive(current.brother, depth, names);
        }
    }

    public void print(String[] names)
    {
        printRecursive(this, 0, names);
    }

    public boolean checkRecursive(ResultTree current,  ResultTree test)
    {
        if (current.name != test.name)
        {
            return false;
        }
        if (current.child != null && test.child != null)
        {
            boolean flag = checkRecursive(current.child,  test.child);
            if (!flag)
                return false;
        }
        else if (current.child != null && test.child == null || current.child == null && test.child != null)
            return false;
        if (current.brother != null && test.brother != null)
        {
            boolean flag = checkRecursive(current.brother,  test.brother);
            if (!flag)
                return false;
        }
        else if (current.brother != null && test.brother == null || current.brother == null && test.brother != null)
            return false;
        return true;
    }

    public boolean checkIfTheSame(ResultTree test)
    {
        return checkRecursive(this, test);
    }
}
