package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.netzfilm.movie.values.NoSuchMovieException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name = "Movies", description = "the movies API to do CRUD")
class MovieRestController {
    private final MovieService movieService;

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all movies", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieTo.class))})})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<MovieTo> findAllMovies() {
        return movieService.getAllMovies();
    }

    @Operation(summary = "Get all movies in xml")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all movies", content = {
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = MovieTo.class))})})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    public Collection<MovieTo> findAllMoviesXml() {
        return movieService.getAllMovies();
    }

    @Operation(summary = "Get a movie by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the movie", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieTo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "movie not found", content = @Content)})
    @GetMapping("/{uuid}")
    public ResponseEntity<MovieTo> getMovie(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.of(Optional.ofNullable(movieService.getMovie(uuid)));
        } catch (NoSuchMovieException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a movie by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated the movie", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "movie not found", content = @Content)})
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @Valid @RequestBody MovieTo movieTo) {
        try {
            movieService.updateMovie(movieTo);
            return ResponseEntity.ok().build();
        } catch (NoSuchMovieException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a movie by uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "movie with given uuid deleted or did not exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid uuid supplied", content = @Content)})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        movieService.deleteMovie(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created the movie", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content)})
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