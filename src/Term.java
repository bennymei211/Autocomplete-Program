import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class Term implements Comparable<Term> {
    private String query; // Query string.
    private long weight; // Query weight.

    // Constructs a term given the associated query string, having weight 0.
    public Term(String query) {
        if (query == null) { // If query is null...
            // throw new NullPointerException.
            throw new NullPointerException("query is null");
        }
        this.query = query; // Initialize this.query to query.
    }

    // Constructs a term given the associated query string and weight.
    public Term(String query, long weight) {
        if (query == null) { // If query is null...
            // throw new NullPointerException.
            throw new NullPointerException("query is null");
        }
        if (weight < 0) { // If weight is less than 0...
            // throw new IllegalArgumentException.
            throw new IllegalArgumentException("Illegal weight");
        }
        this.query = query; // Initialize this.query to query.
        this.weight = weight; // Initialize this.weight to weight.
    }

    // Returns a string representation of this term.
    public String toString() {
        // Return a string containing weight and query separated by a tab.
        return weight + "\t" + query;
    }

    // Returns a comparison of this term and other by query.
    public int compareTo(Term other) {
        String t = this.query; // Set new variable String, t, to this.query.
        String o = other.query; // Set new variable String o, to other.query.
        int result = 0; // Set result to 0.
        if (t.compareTo(o) < 0) { // If t is less than o...
            result = -13; // set result to a negative integer.
        }
        if (t.compareTo(o) == 0) { // If t is equal to o...
            result = 0; // set result to 0.
        }
        if (t.compareTo(o) > 0) { // If t is greater than o...
            result = 12; // set result to a positive integer.
        }
        return result; // Return result
    }

    // Returns a comparator for comparing two terms in reverse order of their weights.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder(); // Return an object of type ReverseWeightOrder.
    }

    // Returns a comparator for comparing two terms by their prefixes of length r.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) { // If r is less than 0...
            // throw new IllegalArgumentException.
            throw new IllegalArgumentException("Illegal r");
        }
        return new PrefixOrder(r); // Return an object of type PrefixOrder, with argument r.
    }

    // Reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            long vw = v.weight; // Set new variable long, vw, to v.weight.
            long ww = w.weight; // Set new variable long, ww, to w.weight.
            int result = 0; // Set result to 0.
            if (vw < ww) { // If vw is less than ww...
                result = 1; // set result to a negative integer.
            }
            if (vw == ww) { // If vw is equal to ww...
                result = 0; // set result to 0.
            }
            if (vw > ww) { // If vw is greater than ww...
                result = -1; // set result to a negative integer.
            }
            return result; // Return result.
        }
    }

    // Prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r; // Prefix length

        // Constructs a new prefix order given the prefix length.
        PrefixOrder(int r) {
            this.r = r; // Initialize this.r to r.
        }

        // Returns a comparison of terms v and w by their prefixes of length r.
        public int compare(Term v, Term w) {
            String vq = v.query; // Set new variable string, vq, to v.query.
            String wq = w.query; // Set new variable string, wq, to w.query.
            // Set a to a substring of v of length min(r, v.query.length()).
            String a = vq.substring(0, Math.min(r, vq.length()));
            // Set b to a substring of w of length min(r, w.query.length()).
            String b = wq.substring(0, Math.min(r, wq.length()));
            int result = 0; // Set result to 0;
            if (a.compareTo(b) < 0) { // If a is less than b...
                result = -1; // set result to a negative integer.
            }
            if (a.compareTo(b) == 0) { // If a is equal to b...
                result = 0; // set result to 0.
            }
            if (a.compareTo(b) > 0) { // If a is greater than b...
                result = 1; // set result to 1.
            }
            return result; // Return result.
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
