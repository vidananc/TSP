public class MyPriorityQueue
{
    private Node[] queue;
    private int length;
    private int loc = 0;
    private int first = 0;
    public MyPriorityQueue(int length)
    {
        this.length = length;
        queue = new Node[length];
    }
    public void add(Node node)
    {
        queue[loc++] = node;
    }
    private int getLeft(int loc)
    {
        return 2 * (loc + 1) - 1;
    }
    private int getRight(int loc)
    {
        return getLeft(loc) + 1;
    }
    private void changeToBalance()
    {
        Node[] copy = new Node[loc - first];
        System.arraycopy(queue, first, copy, 0, copy.length);//保留有效段
        Node temp;
        int current, big;
        boolean isChanged;
        for(int i = copy.length / 2 - 1; i >= 0; i--)
        {
            current = i;
            isChanged = true;
            while(isChanged && getLeft(current) < copy.length)
            {
                big = getLeft(current);
                if(getRight(current) < copy.length)
                {
                    big = copy[big].compareTo(copy[getRight(current)]) > 0 ? big : getRight(current);
                }
                isChanged = false;
                if(copy[big].compareTo(copy[current]) > 0)
                {
                    isChanged = true;
                    temp = copy[current];
                    copy[current] = copy[big];
                    copy[big] = temp;
                    current = big;
                }
            }
        }
        System.arraycopy(copy, 0, queue, first, copy.length);//保留有效段
    }
    public Node getBiggest()
    {
        changeToBalance();
        return queue[first++];
    }
}
