package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final EntityManager entityManager;

    @Override
    public List<MovieTo> getAllMovies() {
        return movieRepository.findAll().stream().map(Movie::toTo).toList();
    }

    @Override
    public UUID addMovie(MovieCreationTo movie) {
        final Movie saved = movieRepository.save(new Movie(movie.title(), movie.releaseDate(), movie.fsk()));
        return saved.getUuid();
    }

    @Override
    public MovieTo getMovie(UUID uuid) {
        return movieRepository.findByUuid(uuid).orElseThrow(() -> new NoSuchMovieException(uuid)).toTo();
    }

    @Override
    @Transactional
    public void updateMovie(MovieTo movie) {
        final Movie entity = movieRepository.findByUuid(movie.uuid()).orElseThrow(() -> new NoSuchMovieException(movie.uuid()));
        // entity aus Hibernate lösen
        entityManager.detach(entity);
        // ändern
        entity.update(movie);
        // wieder einfügen, andernfalls würde die Version einfach aktualisiert werden
        movieRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteMovie(UUID uuid) {
        movieRepository.deleteByUuid(uuid);
    }

    @Override
    public List<MovieTo> searchMovies(Optional<String> title, Optional<LocalDate> releaseDate) {
        List<Movie> moviesFound;
        if (title.isPresent() && releaseDate.isPresent()) {
            moviesFound = movieRepository.findByTitleAndReleaseDate(title.get(), releaseDate.get());
        } else if (title.isPresent()) {
            moviesFound = movieRepository.findByTitle(title.get());
        } else if (releaseDate.isPresent()) {
            moviesFound = movieRepository.findByReleaseDate(releaseDate.get());
        } else {
            moviesFound = movieRepository.findAll();
        }
        return moviesFound.stream().map(Movie::toTo).toList();
    }
}
