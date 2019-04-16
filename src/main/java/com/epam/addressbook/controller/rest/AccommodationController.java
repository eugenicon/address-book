package com.epam.addressbook.controller.rest;

import com.epam.addressbook.data.entity.Accommodation;
import com.epam.addressbook.data.repository.AccommodationRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
public class AccommodationController {
    private final AccommodationRepository accommodationRepository;
    private final MeterRegistry meterRegistry;

    @PostMapping
    public ResponseEntity<Accommodation> create(@RequestBody Accommodation data) {
        return accommodationRepository.create(data)
                .map(entity -> {
                    meterRegistry.gauge("Accommodation.count", accommodationRepository.findAll().map(List::size).orElse(0));
                    return incrementMetricCounter(entity, "Accommodation.created");
                })
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable long id) {
        return accommodationRepository.getById(id)
                .map(entity -> incrementMetricCounter(entity, "Accommodation.getById"))
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> findAll() {
        return accommodationRepository.findAll()
                .map(entity -> incrementMetricCounter(entity, "Accommodation.findAll"))
                .map(data -> new ResponseEntity<>(data, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accommodation> update(@PathVariable long id, @RequestBody Accommodation data) {
        return accommodationRepository.update(id, data)
                .map(entity -> incrementMetricCounter(entity, "Accommodation.update"))
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Accommodation> delete(@PathVariable long id) {
        accommodationRepository.delete(id);
        incrementMetricCounter(id, "Accommodation.delete");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private <T> T incrementMetricCounter(T data, String name) {
        meterRegistry.counter(name).increment();
        return data;
    }
}
