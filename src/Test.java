import java.io.FileNotFoundException;

public class Test
{
    public static void main(String[] args)
    {
        try
        {
            TSP t = new TSP("test.txt");
            System.out.println(t.getBw());
            System.out.println(t.toString());
            BBL b = new BBL("test.txt");
            System.out.println(b.bbl());
            System.out.println(b.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
