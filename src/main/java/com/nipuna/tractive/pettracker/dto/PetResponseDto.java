package com.nipuna.tractive.pettracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nipuna.tractive.pettracker.model.PetType;
import com.nipuna.tractive.pettracker.model.TrackerType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PetResponseDto(
        Long id, PetType petType, TrackerType trackerType,
        Integer ownerId, boolean inZone, Boolean lostTracker) {
}
