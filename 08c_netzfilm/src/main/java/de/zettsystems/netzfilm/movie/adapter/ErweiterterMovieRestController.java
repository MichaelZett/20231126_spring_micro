package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/extended/movies")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Movies Extended", description = "the movies API expanded")
class ErweiterterMovieRestController {
    private final MovieService movieService;

    @GetMapping(value = "/whatyouwant", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Collection<MovieTo>> getTheMoviesHowYouLikeIt(@RequestHeader(value = "Accept") String acceptHeader) {
        final List<MovieTo> allMovies = movieService.getAllMovies();
        HttpHeaders headers = new HttpHeaders();

        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(allMovies, headers, HttpStatus.OK);
        } else if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            headers.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<>(allMovies, headers, HttpStatus.OK);
        } else {
            // Standardmäßig oder Fehlerfall
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(allMovies, headers, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public List<MovieCreationTo> newMovieDataPerFile(@RequestParam("file") MultipartFile file) {
        List<MovieCreationTo> movies = new ArrayList<>();
        if (file.isEmpty()) {
            LOG.info("File is empty!");
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",", -1); // -1 bedeutet, leere Strings am Ende werden nicht entfernt

                    String title = fields[0];
                    LocalDate releaseDate = LocalDate.parse(fields[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    Fsk fsk = Fsk.valueOf(fields[2]);

                    MovieCreationTo movie = new MovieCreationTo(title, releaseDate, fsk);
                    movies.add(movie);
                }
            } catch (Exception e) {
                LOG.error("Datei mit Fehler", e);
            }
        }
        return movies;
    }

    @GetMapping("/search-copies")
    public List<MovieTo> searchMovieCopies(
            @RequestParam(value = "title", required = false) Optional<String> title,
            @RequestParam(value = "releaseDate", required = false) Optional<LocalDate> releaseDate) {
        return movieService.searchMovies(title, releaseDate);
    }

}