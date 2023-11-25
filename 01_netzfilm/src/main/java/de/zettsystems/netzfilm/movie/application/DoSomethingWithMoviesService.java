package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoSomethingWithMoviesService {
    private final MovieService movieService;
    private final CopyRepository copyRepository;

    @EventListener
    public void onStartUp(ContextRefreshedEvent event) {
        movieService.getAllMovies().forEach(m -> LOG.info(m.toString()));
        copyRepository.getCopyOverview().forEach(c -> LOG.info(c.stringRep()));
    }
}
