package com.nipuna.tractive.pettracker.mapper;

import com.nipuna.tractive.pettracker.domain.Cat;
import com.nipuna.tractive.pettracker.domain.Dog;
import com.nipuna.tractive.pettracker.domain.Pet;
import com.nipuna.tractive.pettracker.dto.PetRequestDto;
import com.nipuna.tractive.pettracker.dto.PetResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @Mapping(target = "petType", constant = "CAT")
    Cat toCat(PetRequestDto dto);

    @Mapping(target = "petType", constant = "DOG")
    Dog toDog(PetRequestDto dto);

    // Only Cats have lostTracker; null for other pet types
    @Mapping(target = "lostTracker", expression = "java(p instanceof com.nipuna.tractive.pettracker.domain.Cat ? ((Cat)p).isLostTracker() : null)")
    PetResponseDto toResponseDto(Pet p);
}
