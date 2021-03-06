package com.epam.addressbook.data.repository;

import com.epam.addressbook.data.entity.Accommodation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class JdbcAccommodationRepository implements AccommodationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Accommodation> mapper;
    private final ResultSetExtractor<Accommodation> extractor;

    public JdbcAccommodationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapper = (rs, rowNum) -> new Accommodation(
                rs.getLong("ID"),
                rs.getLong("ADDRESS_ID"),
                rs.getLong("PERSON_ID"),
                rs.getDate("ACCOMMODATION_DATE").toLocalDate(),
                rs.getBoolean("SINGLE_OWNED")
        );
        this.extractor = (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
    }

    @Override
    public Optional<Accommodation> create(Accommodation accommodation) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO ACCOMMODATION (ADDRESS_ID, PERSON_ID, ACCOMMODATION_DATE, SINGLE_OWNED) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, accommodation.getAddressId());
            statement.setLong(2, accommodation.getPersonId());
            statement.setDate(3, Date.valueOf(accommodation.getAccommodationDate()));
            statement.setBoolean(4, accommodation.isSingleOwned());

            return statement;
        }, generatedKeyHolder);

        return getById(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public Optional<Accommodation> getById(long id) {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT ID, ADDRESS_ID, PERSON_ID, ACCOMMODATION_DATE, SINGLE_OWNED FROM ACCOMMODATION WHERE id = ?",
                new Object[]{id},
                extractor));
    }

    @Override
    public Optional<List<Accommodation>> findAll() {
        return Optional.of(jdbcTemplate.query("SELECT ID, ADDRESS_ID, PERSON_ID, ACCOMMODATION_DATE, SINGLE_OWNED FROM ACCOMMODATION", mapper));
    }

    @Override
    public Optional<Accommodation> update(long id, Accommodation accommodation) {
        jdbcTemplate.update("UPDATE ACCOMMODATION " +
                        "SET ADDRESS_ID = ?, PERSON_ID = ?, ACCOMMODATION_DATE = ?,  SINGLE_OWNED = ? " +
                        "WHERE ID = ?",
                accommodation.getAddressId(),
                accommodation.getPersonId(),
                Date.valueOf(accommodation.getAccommodationDate()),
                accommodation.isSingleOwned(),
                id);

        return getById(id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM ACCOMMODATION WHERE ID = ?", id);
    }
}
