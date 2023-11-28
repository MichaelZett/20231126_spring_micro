package de.zettsystems.netzfilm.movie.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.zettsystems.netzfilm.movie.application.MovieService;
import de.zettsystems.netzfilm.movie.values.Fsk;
import de.zettsystems.netzfilm.movie.values.MovieCreationTo;
import de.zettsystems.netzfilm.movie.values.MovieTo;
import de.zettsystems.timeutil.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MovieRestController.class)
class MovieRestControllerTest {

    MockMvc mvc;

    @MockBean
    MovieService movieService;
    @Autowired
    ObjectMapper objectMapper;
    UUID uuid;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        uuid = UUID.randomUUID();
        MovieTo bernd = new MovieTo(uuid, "Bernd", TimeUtil.today(), Fsk.FSK_12, 13);

        List<MovieTo> movies = Arrays.asList(bernd);

        given(movieService.getAllMovies()).willReturn(movies);
        given(movieService.getMovie(uuid)).willReturn(bernd);
    }

    @Test
    void shouldFindAllMovies() throws Exception {

        mvc.perform(get("/api/movies/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.[0].title").value("Bernd"))
                .andExpect(jsonPath("$.[0].version").value(13))
                .andExpect(jsonPath("$.[0].fsk").value("FSK_12"))
                .andExpect(jsonPath("$.[0].releaseDate").value(TimeUtil.today().toString()));

    }

    @Test
    void shouldGetMovie() throws Exception {
        mvc.perform(get("/api/movies/" + uuid.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.title").value("Bernd"))
                .andExpect(jsonPath("$.version").value(13))
                .andExpect(jsonPath("$.fsk").value("FSK_12"))
                .andExpect(jsonPath("$.releaseDate").value(TimeUtil.today().toString()));
    }

    @Test
    void shouldUpdate() throws Exception {
        String body = """
                { "uuid": "%s",\
                  "title": "%s",\
                  "releaseDate": "%s",\
                  "fsk": "%s",\
                  "version": "%s"\
                }""".formatted(uuid.toString(), "Bernd", LocalDate.of(2023, 6, 1).toString(), "FSK_12", 13);

        // Alternative zu json selber schreiben
//        MovieTo bernd1 = new MovieTo(uuid, "Bernd", LocalDate.of(2023, 6, 1), Fsk.FSK_12, 13);
//        String bernd = objectMapper.writeValueAsString(bernd1);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/movies/" + uuid.toString())
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());

        ArgumentCaptor<MovieTo> captor = ArgumentCaptor.forClass(MovieTo.class);
        verify(movieService).updateMovie(captor.capture());
        assertThat(captor.getValue().releaseDate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }

    @Test
    void shouldDelete() throws Exception {
        mvc.perform(delete("/api/movies/" + uuid.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldCreate() throws Exception {
        String body = """
                { "title": "%s",\
                  "releaseDate": "%s",\
                  "fsk": "%s"\
                }""".formatted("Bernd 2", LocalDate.of(2023, 6, 1).toString(), "FSK_12");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/movies/")
                .accept("*/*")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);

        final UUID uuid = UUID.randomUUID();
        when(movieService.addMovie(any(MovieCreationTo.class))).thenReturn(uuid);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/movies/%s".formatted(uuid.toString()))));

        ArgumentCaptor<MovieCreationTo> captor = ArgumentCaptor.forClass(MovieCreationTo.class);
        verify(movieService).addMovie(captor.capture());
        assertThat(captor.getValue().releaseDate()).isEqualTo(LocalDate.of(2023, 6, 1));
    }
}