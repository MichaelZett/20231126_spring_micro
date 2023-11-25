package de.zettsystems.java21;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class SequencedCollections {

    public static void main(String[] args) {
        List<String> items = List.of("A", "B", "C", "D");

        // 3 Interface, 3* gleiche Aufgabe, 3* unterschiedliche Methoden
        String first = items.get(0);
        System.out.println(first);
        first = items.iterator().next();
        System.out.println(first);
        String last = items.get(items.size() - 1);
        System.out.println(last);

        Deque<String> deque = new ArrayDeque<>();
        deque.addAll(items);
        first = deque.getFirst();
        System.out.println(first);
        last = deque.getLast();
        System.out.println(last);

        SortedSet<String> sortedSet = new TreeSet<>();
        sortedSet.addAll(items);
        first = sortedSet.first();
        System.out.println(first);
        last = sortedSet.last();
        System.out.println(last);

        //Java 21 Sequenced Collection
        first = items.getFirst();
        System.out.println(first);
        last = items.getLast();
        System.out.println(last);

        first = deque.getFirst();
        System.out.println(first);
        last = deque.getLast();
        System.out.println(last);

        first = sortedSet.getFirst();
        System.out.println(first);
        last = sortedSet.getLast();
        System.out.println(last);
    }
}
