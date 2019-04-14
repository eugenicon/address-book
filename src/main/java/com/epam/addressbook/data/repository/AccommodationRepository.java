package com.epam.addressbook.data.repository;

import com.epam.addressbook.data.entity.Accommodation;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository {
    Optional<Accommodation> create(Accommodation entity);

    Optional<Accommodation> getById(long id);

    Optional<List<Accommodation>> findAll();

    Optional<Accommodation> update(long id, Accommodation entity);

    void delete(long id);
}
