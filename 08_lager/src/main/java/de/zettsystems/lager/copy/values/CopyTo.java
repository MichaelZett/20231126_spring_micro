package de.zettsystems.lager.copy.values;

import java.util.UUID;

public record CopyTo(UUID movieUuid, CopyType copyType, int number) {
}
