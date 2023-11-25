package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public String movies(Model model) {
        final List<MovieTo> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies/movies";
    }

}