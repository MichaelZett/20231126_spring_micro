package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.values.CopyOverview;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
class CopyRestController {
    private final CopyRepository copyRepository;

    @GetMapping("/")
    public Collection<CopyTo> findAllCopies() {
        return copyRepository.findAll().stream().map(Copy::toTo).toList();
    }

    @GetMapping("/overview")
    public Collection<CopyOverview> showCopyOverview() {
        return copyRepository.getCopyOverview();
    }

}