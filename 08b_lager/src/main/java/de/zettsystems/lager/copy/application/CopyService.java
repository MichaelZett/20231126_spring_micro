package de.zettsystems.lager.copy.application;

import de.zettsystems.lager.copy.values.CopyTo;

public interface CopyService {
    String addCopies(CopyTo copyTo);

    String addCopiesWithTimeout(CopyTo copyTo);
}
