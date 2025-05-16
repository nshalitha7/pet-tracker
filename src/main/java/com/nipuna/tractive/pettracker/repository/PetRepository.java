package com.nipuna.tractive.pettracker.repository;

import com.nipuna.tractive.pettracker.model.Pet;
import com.nipuna.tractive.pettracker.dto.SummaryItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("""
               SELECT new com.nipuna.tractive.pettracker.dto.SummaryItemDto(
                       p.petType, p.trackerType, COUNT(p))
                 FROM Pet p
                 WHERE p.inZone = false
                 GROUP BY p.petType, p.trackerType
            """)
    List<SummaryItemDto> countOutsideGrouped();
}
