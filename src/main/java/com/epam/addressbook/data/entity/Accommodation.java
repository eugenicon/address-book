package com.epam.addressbook.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {
    private long id;
    private long addressId;
    private long personId;
    private LocalDate accommodationDate;
    private boolean isSingleOwned;

    public Accommodation(long addressId, long personId, LocalDate accommodationDate, boolean isSingleOwned) {
        this.addressId = addressId;
        this.personId = personId;
        this.accommodationDate = accommodationDate;
        this.isSingleOwned = isSingleOwned;
    }
}
