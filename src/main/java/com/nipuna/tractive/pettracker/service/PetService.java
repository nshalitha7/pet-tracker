package com.nipuna.tractive.pettracker.service;

import com.nipuna.tractive.pettracker.domain.Pet;
import com.nipuna.tractive.pettracker.domain.PetType;
import com.nipuna.tractive.pettracker.dto.PetRequestDto;
import com.nipuna.tractive.pettracker.dto.PetResponseDto;
import com.nipuna.tractive.pettracker.dto.SummaryItemDto;
import com.nipuna.tractive.pettracker.mapper.PetMapper;
import com.nipuna.tractive.pettracker.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PetService {

    private final PetRepository repo;
    private final PetMapper mapper;

    public PetResponseDto save(PetRequestDto dto) {
        // validation - only cats have lostTracker attribute
        if (dto.petType() == PetType.DOG && dto.lostTracker() != null) {
            throw new IllegalArgumentException("Dogs must not have a lostTracker attribute.");
        }

        Pet entity = switch (dto.petType()) {
            case CAT -> mapper.toCat(dto);
            case DOG -> mapper.toDog(dto);
        };
        Pet saved = repo.save(entity);
        log.info("Saved {} with ownerId {}", dto.petType(), dto.ownerId());

        return mapper.toResponseDto(saved);
    }

    public List<PetResponseDto> findAll() {
        return repo.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public List<SummaryItemDto> summaryOutside() {
        return repo.countOutsideGrouped();
    }
}
