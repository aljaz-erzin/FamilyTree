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
}
