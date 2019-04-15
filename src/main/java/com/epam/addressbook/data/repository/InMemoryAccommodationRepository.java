package com.epam.addressbook.data.repository;

import com.epam.addressbook.data.entity.Accommodation;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class InMemoryAccommodationRepository implements AccommodationRepository {
    private final Map<Long, Accommodation> data = new HashMap<>();

    @Override
    public Optional<Accommodation> create(Accommodation entity) {
        long lastId = data.values().stream().mapToLong(Accommodation::getId).max().orElse(0);
        entity.setId(lastId + 1);
        data.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Accommodation> getById(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<List<Accommodation>> findAll() {
        return Optional.of(new ArrayList<>(data.values()));
    }

    @Override
    public Optional<Accommodation> update(long id, Accommodation entity) {
        return getById(id).map(existing -> {
            entity.setId(id);
            data.replace(id, entity);
            return entity;
        });
    }

    @Override
    public void delete(long id) {
        data.remove(id);
    }
}
