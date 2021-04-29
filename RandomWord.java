import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String champion = "";

        while (!StdIn.isEmpty()) {
            ++i;
            String readString = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = readString;
            }
        }

        StdOut.println(champion);
    }
}
