package com.nipuna.tractive.pettracker.dto;

import com.nipuna.tractive.pettracker.model.PetType;
import com.nipuna.tractive.pettracker.model.TrackerType;
import jakarta.validation.constraints.NotNull;

public record PetRequestDto(
        @NotNull PetType petType,
        @NotNull TrackerType trackerType,
        @NotNull Integer ownerId,
        boolean inZone,
        Boolean lostTracker
) {
}

