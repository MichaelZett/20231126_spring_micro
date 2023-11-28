package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.rent.values.RentFullTo;
import de.zettsystems.netzfilm.rent.values.RentTo;

import java.util.Collection;

public interface RentService {
    void rentAMovie(RentTo rentTo);

    Collection<RentFullTo> findAllRents();
}
