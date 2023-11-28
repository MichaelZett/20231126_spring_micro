package de.zettsystems.netzfilm.movie.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<Movie> findByTitleAndReleaseDate(String s, LocalDate localDate);

    List<Movie> findByTitle(String s);

    List<Movie> findByReleaseDate(LocalDate localDate);
}
