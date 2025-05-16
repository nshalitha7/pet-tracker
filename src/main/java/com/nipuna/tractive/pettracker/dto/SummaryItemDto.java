package com.nipuna.tractive.pettracker.dto;

import com.nipuna.tractive.pettracker.domain.PetType;
import com.nipuna.tractive.pettracker.domain.TrackerType;

public record SummaryItemDto(
        PetType petType, TrackerType trackerType, long count) { }
