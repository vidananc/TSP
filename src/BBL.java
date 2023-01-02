import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BBL
{
    private int[] step;
    private int[] br;
    private int[][] d;
    private int[][] map;
    private int loc;
    private List<Integer> result = new ArrayList<>();
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
    private void getFirstAndSecondBig()//获取每个顶点的邻接边的最小两个边，使用堆排初始建堆来实现
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
                        big = copy[big] < copy[(current + 1) * 2] ? big : (current + 1) * 2;
                    }
                    isChanged = false;
                    if(copy[current] > copy[big])
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
            map[i][1] = Math.min(copy[1], copy[2]);
        }
    }
    private int getLb(Node next)
    {
        List<Integer> list = next.step;
        int map = next.map;
        int max1, max2;
        int lb = 0;
        for(int i = 0; i < list.size(); i++)
        {
            if(i == 0)
            {
                lb += d[list.get(i) - 1][list.get(i + 1) - 1];
                max1 = this.map[list.get(i) - 1][0];
                max2 = this.map[list.get(i) - 1][1];
                if(d[list.get(i) - 1][list.get(i + 1) - 1] == max1)
                {
                    lb += max2;
                }
                else
                {
                    lb += max1;
                }
            }
            else if(i == list.size() - 1)
            {
                max1 = this.map[list.get(i) - 1][0];
                max2 = this.map[list.get(i) - 1][1];
                if(d[list.get(i - 1) - 1][list.get(i) - 1] == max1)
                {
                    lb += max2;
                }
                else
                {
                    lb += max1;
                }
            }
            else
            {
                lb += d[list.get(i - 1) - 1][list.get(i) - 1];
                lb += d[list.get(i) - 1][list.get(i + 1) - 1];
            }
        }
        for(int i = 1; i < step.length; i++)//遍历位图，将剩余的顶点计算哈密尔顿通路的边
        {
            if((map & (1 << i)) == 0)
            {
                max1 = this.map[i - 1][0];
                max2 = this.map[i - 1][1];
                lb += max1;
                lb += max2;
            }
        }
        return lb;
    }
    public int bbl()//分支限界法
    {
        MyPriorityQueue queue = new MyPriorityQueue(100);
        Node current, next;
        Node result = null;
        int map;
        int bw = Integer.MAX_VALUE;
        current = new Node(1, 1);
        current.setStep(new ArrayList<>());
        int i = 2;
        while(i < step.length)
        {
            map = current.map;
            for(int l = 1; l < step.length; l++)
            {
                if((map & (1 << l)) == 0)//未经过的顶点
                {
                    next = new Node(i, l);
                    next.setValue(current.value + d[current.currentPoint - 1][l - 1]);
                    next.setStep(current.step);
                    next.setLb(getLb(next));
                    if(i != step.length - 1)
                    {
                        queue.add(next);
                    }
                    else
                    {
                        next.setValue(next.value + d[next.currentPoint - 1][0]);
                        if(next.value < bw)
                        {
                            bw = next.value;
                            result = next;
                        }
                    }
                }
            }
            current = queue.getBiggest();
            i = current.layer + 1;
            if(current.lb >= bw)
            {
                break;
            }
        }
        this.result = result.step;
        return bw;
    }
    public String toString()
    {
        return result.toString();
    }
}
class Node implements Comparable<Node>
{
    int layer;
    int value = 0;
    int lb;
    int currentPoint;
    int map;
    List<Integer> step = new ArrayList<>();
    public Node(int layer, int currentPoint)
    {
        this.layer = layer;
        this.currentPoint = currentPoint;
    }
    public void setStep(List<Integer> current)
    {
        for(int i = 0; i < current.size(); i++)
        {
            step.add(current.get(i));
            map += (1 << current.get(i));
        }
        this.step.add(currentPoint);
        map += (1 << currentPoint);
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