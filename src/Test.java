import java.io.FileNotFoundException;

public class Test
{
    public static void main(String[] args)
    {
        try
        {
            TSP t = new TSP("cityRoad.txt");
            System.out.println(t.getBw());
            System.out.println(t.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
