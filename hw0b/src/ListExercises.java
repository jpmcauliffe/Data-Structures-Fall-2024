import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int total = 0;
        for (int i : L) {
            total += i;
        }
        return total;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evens = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }
        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> inBoth = new ArrayList<>();
        for (int i : L1) {
            for (int x : L2) {
                if (x == i) {
                    inBoth.add(x);
                }
            }
        }
        return inBoth;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int total = 0;
        for (String w : words) {
            int wordLength = w.length();
            int counter = 0;
            while (counter < wordLength) {
                if (c == w.charAt(counter)) {
                    total++;
                }
                counter++;
            }
        }
        return total;
    }
}
