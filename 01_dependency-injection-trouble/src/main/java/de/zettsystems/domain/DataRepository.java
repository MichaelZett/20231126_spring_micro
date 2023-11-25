package de.zettsystems.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataRepository {
    private final Map<String, String> repo = new ConcurrentHashMap<>();

    public Optional<String> getDataByKey(String key) {
        return Optional.ofNullable(repo.get(key));
    }

    public Optional<String> putData(String key, String value) {
        return Optional.ofNullable(repo.put(key, value));
    }
}
