package de.zettsystems.netzfilm.movie.values;


import org.springframework.beans.factory.annotation.Value;

public interface CopyOverview {
    String getTitle();

    String getDvd();

    String getVhs();

    @Value("#{target.title + ' DVD: ' + target.dvd + ' VHS: ' + target.vhs}")
    String stringRep();
}
