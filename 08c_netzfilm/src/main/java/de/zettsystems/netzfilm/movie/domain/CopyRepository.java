package de.zettsystems.netzfilm.movie.domain;

import de.zettsystems.netzfilm.movie.values.CopyOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findAllByMovie(Movie movie);

    Optional<Copy> findByUuid(UUID uuid);

    List<Copy> findAllByLentIsFalse();

    @Query("SELECT c FROM Copy c JOIN FETCH c.movie WHERE c.lent=false")
    List<Copy> customFindAllByLentIsFalse();

    @Query(value = """
            Select
             title,
             count(case type when 'DVD' then 1 else null end) as dvd,
             count(case type when 'VHS' then 1 else null end) as vhs
             from Copy c join Movie m on c.movie_id = m.id
             group by title
            """, nativeQuery = true)
    Collection<CopyOverview> getCopyOverview();

}
