package de.zettsystems.netzfilm.movie.values;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Value;

@Schema(name = "Copy Overview", description = "Übersicht über vorhandene Kopien")
public interface CopyOverview {
    @Schema(description = "Filmtitel")
    String getTitle();

    @Schema(description = "Anzahl DVD")
    String getDvd();

    @Schema(description = "Anzahl VHS")
    String getVhs();

    @Value("#{target.title + ' DVD: ' + target.dvd + ' VHS: ' + target.vhs}")
    String stringRep();
}
