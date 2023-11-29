package de.zettsystems.netzfilm.movie.adapter;

import com.google.common.collect.ImmutableMultiset;
import de.zettsystems.netzfilm.movie.application.CopyService;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.values.CopyAddTo;
import de.zettsystems.netzfilm.movie.values.CopyOverview;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/copies")
@RequiredArgsConstructor
@Tag(name = "Copies", description = "the copies API")
class CopyRestController {
    private final CopyRepository copyRepository;
    private final CopyService copyService;

    @Operation(summary = "Get all copies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all copies", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CopyTo.class))})})
    @GetMapping("/")
    public Collection<CopyTo> findAllCopies() {
        return copyRepository.findAll().stream().map(Copy::toTo).toList();
    }

    @Operation(summary = "Get an overview of copies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "show overview", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CopyOverview.class))})})
    @GetMapping("/overview")
    public Collection<CopyOverview> showCopyOverview() {
        return copyRepository.getCopyOverview();
    }

    @PostMapping("/")
    public ResponseEntity<Void> add(@Valid @RequestBody CopyAddTo copyTo) {
        copyService.addCopies(copyTo);
        return ResponseEntity.ok().build();
    }

}