import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) { // If a, key, or c is equal to null...
            // throw new NullPointerException.
            throw new NullPointerException("a, key, or c is null");
        }
        int lo = 0; // Set index lo to 0.
        int hi = a.length - 1; // Set index hi to the length of a subtracted by 1.
        int index = -1; // Set index to -1.
        while (lo <= hi) { // As long as lo is less than or equal to hi...
            int mid = lo + (hi - lo) / 2; // Set the middle index to lo + (hi-lo) / 2.
            // Set int, cmp, to the result of c.compare(key, a[mid]).
            int cmp = c.compare(key, a[mid]);
            if (cmp < 0) { // If cmp is less than 0...
                hi = mid - 1; // Set hi to mid subtracted by 1.
            } else if (cmp > 0) { // Else if cmp is greater than 0...
                lo = mid + 1; // Set lo to mid plus 1.
            } else { // Otherwise...
                hi = mid - 1; // set hi to mid subtracted by 1 and...
                index = mid; // index to mid.
            }
        }
        return index; // Return index.
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) { // If a, key, or c is null...
            throw new NullPointerException("a, key, or c is null"); // throw NullPointerException.
        }
        int lo = 0; // Set index lo to 0.
        int hi = a.length - 1; // Set index hi to the length of a subtracted by 1.
        int index = -1; // Set index to -1.
        while (lo <= hi) { // As long as lo is less than or equal to hi...
            int mid = lo + (hi - lo) / 2; // Set the middle index to lo + (hi-lo) / 2.
            // Set int, cmp, to the result of c.compare(key, a[mid]).
            int cmp = c.compare(key, a[mid]);
            if (cmp < 0) { // If cmp is less than 0...
                hi = mid - 1; // set hi to mid subtracted by 1.
            } else if (cmp > 0) { // Else if cmp is greater than 0...
                lo = mid + 1; // set lo to mid plus 1.
            } else { // Otherwise...
                lo = mid + 1; // set lo to mid plus 1 and...
                index = mid; // index to mid.
            }
        }
        return index; // Return index.
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
