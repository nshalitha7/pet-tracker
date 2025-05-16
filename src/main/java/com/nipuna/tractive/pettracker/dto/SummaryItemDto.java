package com.nipuna.tractive.pettracker.dto;

import com.nipuna.tractive.pettracker.model.PetType;
import com.nipuna.tractive.pettracker.model.TrackerType;

public record SummaryItemDto(
        PetType petType, TrackerType trackerType, long count) {
}
