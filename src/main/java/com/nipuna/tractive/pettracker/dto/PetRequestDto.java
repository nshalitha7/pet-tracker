package com.nipuna.tractive.pettracker.dto;

import com.nipuna.tractive.pettracker.domain.PetType;
import com.nipuna.tractive.pettracker.domain.TrackerType;
import jakarta.validation.constraints.NotNull;

public record PetRequestDto(
        @NotNull PetType petType,
        @NotNull TrackerType trackerType,
        @NotNull Integer ownerId,
        boolean inZone,
        Boolean lostTracker
) { }

