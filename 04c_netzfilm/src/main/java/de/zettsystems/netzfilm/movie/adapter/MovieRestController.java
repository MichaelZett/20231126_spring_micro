package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
class MovieRestController {
    private final MovieService movieService;

    @GetMapping("/")
    public Collection<MovieTo> findAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MovieTo> getMovie(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.of(Optional.ofNullable(movieService.getMovie(uuid)));
        } catch (NoSuchMovieException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @Valid @RequestBody MovieTo movieTo) {
        try {
            movieService.updateMovie(movieTo);
            return ResponseEntity.ok().build();
        } catch (NoSuchMovieException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        movieService.deleteMovie(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody MovieCreationTo movieTo) {
        final UUID uuid = movieService.addMovie(movieTo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}