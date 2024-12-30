import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> letterToNumMap = new HashMap<>();

        letterToNumMap.put('a', 1);
        letterToNumMap.put('b', 2);
        letterToNumMap.put('c', 3);
        letterToNumMap.put('d', 4);
        letterToNumMap.put('e', 5);
        letterToNumMap.put('f', 6);
        letterToNumMap.put('g', 7);
        letterToNumMap.put('h', 8);
        letterToNumMap.put('i', 9);
        letterToNumMap.put('j', 10);
        letterToNumMap.put('k', 11);
        letterToNumMap.put('l', 12);
        letterToNumMap.put('m', 13);
        letterToNumMap.put('n', 14);
        letterToNumMap.put('o', 15);
        letterToNumMap.put('p', 16);
        letterToNumMap.put('q', 17);
        letterToNumMap.put('r', 18);
        letterToNumMap.put('s', 19);
        letterToNumMap.put('t', 20);
        letterToNumMap.put('u', 21);
        letterToNumMap.put('v', 22);
        letterToNumMap.put('w', 23);
        letterToNumMap.put('x', 24);
        letterToNumMap.put('y', 25);
        letterToNumMap.put('z', 26);

        return letterToNumMap;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> squaresMap = new HashMap<>();
        List<Integer> squaresList = new ArrayList<>();
        for (int i : nums) {
            int x = i * i;
            squaresList.add(x);
            squaresMap.put(i, x);
        }
        return squaresMap;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> countWordsMap = new HashMap<>();
        for (String w : words) {
        // if already in map, add one to value
        // else, add to map with value 1
            if (countWordsMap.containsKey(w)) {
                int val = countWordsMap.get(w);
                val++;
                countWordsMap.put(w, val);
            }
            else {
                countWordsMap.put(w, 1);
            }
        }
        return countWordsMap;
    }
}
