package com.a702.finafanbe.core.entertainer.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntertainerRepository extends JpaRepository<Entertainer, Long> {
    Optional<Entertainer> findByEntertainerName(String entertainer);

    Optional<Entertainer> findByEntertainerId(Long entertainerId);
}
