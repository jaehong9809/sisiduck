package com.a702.finafanbe.core.entertainer.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntertainerRepository extends JpaRepository<Entertainer, Long> {
    Optional<Entertainer> findByEntertainerId(Long entertainerId);

    List<Entertainer> findByEntertainerNameContainingIgnoreCase(String keyword);

    @Query("SELECT e FROM Entertainer e WHERE LOWER(e.entertainerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.fandomName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Entertainer> searchByNameOrFandom(@Param("keyword") String keyword);
}
