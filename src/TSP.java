import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TSP
{
    private int[][] d;
    private int[] step;
    private int[] br;
    private int bw = Integer.MAX_VALUE;
    private int cw = 0;
    public TSP(String fileName) throws FileNotFoundException
    {
        createMap(fileName);
        tsp(2);
    }
    private void createMap(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        Scanner checkInput;
        int n = Integer.parseInt(input.nextLine());
        d = new int[n][n];
        step = new int[n + 1];
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
    private boolean cut(int t)//剪枝函数
    {
        if(d[step[t - 1] - 1][step[t] - 1] == -1) return false;//t-1步和t步不连通
        return cw < bw;
    }
    private void swap(int l1, int l2)
    {
        int temp = step[l1];
        step[l1] = step[l2];
        step[l2] = temp;
    }
    private void tsp(int t)
    {
        if(t > step.length - 1)
        {
            cw += d[step[t - 1] - 1][step[1] - 1];
            if(cw < bw)
            {
                bw = cw;
                System.arraycopy(step, 1, br, 1, step.length - 1);
            }
            cw -= d[step[t - 1] - 1][step[1] - 1];
            return ;
        }
        for(int i = t; i < step.length; i++)
        {
            swap(t, i);
            cw += d[step[t - 1] - 1][step[t] - 1];
            if(cut(t)) tsp(t + 1);
            cw -= d[step[t - 1] - 1][step[t] - 1];
            swap(t, i);
        }
    }
    public int getBw()
    {
        return bw;
    }
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for(int i = 1; i < br.length; i++)
        {
            result.append(String.valueOf(br[i]) + " ");
        }
        return result.toString();
    }
}
