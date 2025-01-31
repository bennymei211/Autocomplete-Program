import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private Term[] terms; // Array of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) { // If terms is equal to null...
            // throw new NullPointerException.
            throw new NullPointerException("terms is null");
        }
        // Initialize this.terms to a defensive copy of terms.
        this.terms = Arrays.copyOf(terms, terms.length);
        // Sort this.terms in lexicographic order.
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        if (prefix == null) { // If prefix is equal to null...
            // throw new NullPointerException.
            throw new NullPointerException("prefix is null");
        }
        Term term = new Term(prefix); // Convert prefix from a string to a Term object
        // Create a comparator, termc, to compare two prefixes by their prefixes at length r.
        Comparator<Term> termc = Term.byPrefixOrder(prefix.length());
        // Find the index i of the first term in terms that starts with prefix.
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, termc);
        // Set n to the number of terms that match with prefix.
        int n = numberOfMatches(prefix);
        // Construct an empty array, matches, the size of how many terms match the prefix.
        Term[] matches = new Term[n];
        for (int j = 0; j < n; j++) { // From range [0, n)...
            matches[j] = terms[i + j]; // initialize matches at index j to terms at index i + j
        }
        // Sort matches in reverse order of weight.
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches; // Return matches.
    }


    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) { // If prefix is equal to null...
            // throw new NullPointerException.
            throw new NullPointerException("prefix is null");
        }
        Term term = new Term(prefix); // Convert prefix from a string to a Term object.
        // Create a comparator, termc, to compare two prefixes by their prefixes at length r.
        Comparator<Term> termc = Term.byPrefixOrder(prefix.length());
        // Find the index i of the first term in terms that starts with prefix.
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, termc);
        // Find the index j of the first term in terms that starts with prefix.
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, termc);
        if (terms.length == 0) { // If the length of terms is 0...
            return 0; // return 0.
        }
        if (i == 0 && j == 0) { // If i and j is equal to 0...
            return 0; // return 0...
        } else { // Otherwise...
            return (j - i) + 1; // return (j-i) + 1.
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
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
