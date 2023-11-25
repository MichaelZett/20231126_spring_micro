package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.values.CopyTo;
import de.zettsystems.netzfilm.movie.values.NoSuchCopyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;

    @Override
    public List<CopyTo> findAllRentableCopies() {
        return copyRepository.findAllByLentIsFalse().stream().map(Copy::toTo).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public String getTitleAndFormat(UUID copyUuid) {
        final Copy copy = copyRepository.findByUuid(copyUuid).orElseThrow(() -> new NoSuchCopyException(copyUuid));
        return copy.getMovie().getTitle() + " - " + copy.getType().toString();
    }
}
