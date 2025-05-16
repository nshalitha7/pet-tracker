package com.nipuna.tractive.pettracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nipuna.tractive.pettracker.domain.PetType;
import com.nipuna.tractive.pettracker.domain.TrackerType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetResponseDto(
        Long id, PetType petType, TrackerType trackerType,
        Integer ownerId, boolean inZone, Boolean lostTracker) {
}
