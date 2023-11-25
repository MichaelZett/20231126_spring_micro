package de.zettsystems.java16.listof;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UnmodifiableCollectionCreation {

    public List<String> getAllEntriesAsList() {
        // vorher:
        //        return retrieveEntries().stream().collect(Collectors.toUnmodifiableList());
        // ab Java 16:
        return retrieveEntries().stream().toList();
    }

    private Collection<String> retrieveEntries() {
        return Arrays.asList("A", "B");
    }
}
