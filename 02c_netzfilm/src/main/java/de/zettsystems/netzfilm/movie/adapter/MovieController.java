package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public String movies(Model model) {
        final List<MovieTo> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("fsks", Fsk.values());
        model.addAttribute("movie", new MovieCreationTo(null, null, null));
        return "movies/movies";
    }

    @PostMapping("/addmovie")
    public String addMovie(@Valid @ModelAttribute("movie") MovieCreationTo movie, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            final List<MovieTo> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            return "movies/movies";
        }
        movieService.addMovie(movie);
        redirectAttributes.addFlashAttribute("message", "Successfully added " + movie.title());
        return "redirect:/movies";
    }

}