package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<MovieTo> getAllMovies();

    UUID addMovie(MovieCreationTo movie);

    MovieTo getMovie(UUID uuid);

    void updateMovie(MovieTo Movie);

    void deleteMovie(UUID uuid);

}
