package de.zettsystems.netzfilm.rent.adapter;

import de.zettsystems.netzfilm.rent.application.RentService;
import de.zettsystems.netzfilm.rent.values.RentFullTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
@Tag(name = "Rents", description = "the rents API")
public class RentRestController {
    private final RentService rentService;

    @Operation(summary = "Get all rents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "got all rents", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RentFullTo.class))})})
    @GetMapping("/")
    public Collection<RentFullTo> findAllRents() {
        return rentService.findAllRents();
    }

}
