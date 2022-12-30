import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BBL
{
    private int[] step;
    private int[] br;
    private int[][] d;
    private int[][] map;
    private int loc;
    public BBL(String fileName) throws FileNotFoundException {
        createMap(fileName);
        getFirstAndSecondBig();
    }
    private void createMap(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        Scanner checkInput;
        int n = Integer.parseInt(input.nextLine());
        d = new int[n][n];
        step = new int[n + 1];
        map = new int[n][2];
        for(int i = 1; i < step.length; i++)
        {
            step[i] = i;
        }
        br = new int[n + 1];
        int i = 0, j = 0;
        while(input.hasNext())
        {
            checkInput = new Scanner(input.nextLine());
            while(checkInput.hasNext())
            {
                d[i][j] = checkInput.nextInt();
                j++;
            }
            i++;
            j = 0;
        }
    }
    private void getFirstAndSecondBig()
    {
        int current, big, temp;
        int[] copy = new int[d[0].length];
        boolean isChanged;
        for(int i = 0; i < d.length; i++)
        {
            System.arraycopy(d[i], 0, copy, 0, d[i].length);
            for(int j = copy.length / 2 - 1; j >= 0; j--)
            {
                current = j;
                isChanged = true;
                while(isChanged && (current + 1) * 2 - 1 < copy.length)
                {
                    big = (current + 1) * 2 - 1;
                    if((current + 1) * 2 < d[i].length)
                    {
                        big = copy[big] > copy[(current + 1) * 2] ? big : (current + 1) * 2;
                    }
                    isChanged = false;
                    if(copy[current] < copy[big])
                    {
                        temp = copy[current];
                        copy[current] = copy[big];
                        copy[big] = temp;
                        isChanged = true;
                        current = big;
                    }
                }
            }
            map[i][0] = copy[0];
            map[i][1] = Math.max(copy[1], copy[2]);
        }
    }
    private void bbl()//分支限界法
    {
        int i = 1;
        MyPriorityQueue queue = new MyPriorityQueue(100);
        Node current, next;
        for(int k = 1; k < step.length; k++)
        {
            step[k] = k;
        }
        loc = 2;
        current = new Node(1, 1);
        current.step.add(1);
        while(i < step.length)
        {
            for(int k = loc; k < step.length; k++)
            {
                next = new Node(i + 1, k);
                next.step.addAll(current.step);//保存父节点的路程信息
                next.value = current.value + d[current.currentPoint - 1][next.currentPoint - 1];
                next.setLb(1);
            }
        }
    }
}
class Node implements Comparable<Node>
{
    int layer;
    int value = 0;
    int lb;
    int currentPoint;
    List<Integer> step = new ArrayList<>();
    public Node(int layer, int currentPoint)
    {
        this.layer = layer;
        this.currentPoint = currentPoint;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public void setLb(int lb)
    {
        this.lb = lb;
    }

    @Override
    public int compareTo(Node o) {
        return o.lb - this.lb;
    }
}