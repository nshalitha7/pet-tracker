package com.nipuna.tractive.pettracker.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cat extends Pet {
    private boolean lostTracker;
}
