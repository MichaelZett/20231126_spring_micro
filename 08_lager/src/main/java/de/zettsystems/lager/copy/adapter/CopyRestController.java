package de.zettsystems.lager.copy.adapter;

import de.zettsystems.lager.copy.application.CopyService;
import de.zettsystems.lager.copy.values.CopyTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
class CopyRestController {
    private final CopyService copyService;

    @PostMapping("/")
    public ResponseEntity<Void> add(@Valid @RequestBody CopyTo copyTo) {
        copyService.addCopies(copyTo);
        return ResponseEntity.ok().build();
    }

}