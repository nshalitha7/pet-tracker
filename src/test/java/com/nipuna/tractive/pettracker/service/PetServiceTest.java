package com.nipuna.tractive.pettracker.service;

import com.nipuna.tractive.pettracker.model.Cat;
import com.nipuna.tractive.pettracker.model.Dog;
import com.nipuna.tractive.pettracker.model.Pet;
import com.nipuna.tractive.pettracker.model.PetType;
import com.nipuna.tractive.pettracker.model.TrackerType;
import com.nipuna.tractive.pettracker.dto.PetRequestDto;
import com.nipuna.tractive.pettracker.dto.PetResponseDto;
import com.nipuna.tractive.pettracker.dto.SummaryItemDto;
import com.nipuna.tractive.pettracker.mapper.PetMapper;
import com.nipuna.tractive.pettracker.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository repo;
    @Mock
    PetMapper mapper;

    @InjectMocks
    PetService service;

    @Test
    void shouldSaveCatWithLostTracker() {
        PetRequestDto dto = new PetRequestDto(PetType.CAT, TrackerType.SMALL, 1001, false, true);
        Cat cat = new Cat();
        cat.setOwnerId(1001);
        cat.setInZone(false);
        cat.setTrackerType(TrackerType.SMALL);
        cat.setLostTracker(true);

        PetResponseDto expected = new PetResponseDto(1L, PetType.CAT, TrackerType.SMALL, 1001, false, true);

        when(mapper.toCat(dto)).thenReturn(cat);
        when(repo.save(cat)).thenReturn(cat);
        when(mapper.toResponseDto(cat)).thenReturn(expected);

        PetResponseDto result = service.save(dto);

        assertEquals(expected, result);
        verify(repo).save(cat);
    }

    @Test
    void shouldSaveDogWithoutLostTracker() {
        PetRequestDto dto = new PetRequestDto(PetType.DOG, TrackerType.BIG, 2002, false, null);
        Dog dog = new Dog();
        dog.setOwnerId(2002);
        dog.setInZone(false);
        dog.setTrackerType(TrackerType.BIG);

        PetResponseDto expected = new PetResponseDto(2L, PetType.DOG, TrackerType.BIG, 2002, false, null);

        when(mapper.toDog(dto)).thenReturn(dog);
        when(repo.save(dog)).thenReturn(dog);
        when(mapper.toResponseDto(dog)).thenReturn(expected);

        PetResponseDto result = service.save(dto);

        assertEquals(expected, result);
        verify(repo).save(dog);
    }

    @Test
    void shouldReturnAllPets() {
        List<Pet> pets = List.of(new Dog());
        when(repo.findAll()).thenReturn(pets);
        when(mapper.toResponseDto(any())).thenReturn(new PetResponseDto(1L, PetType.DOG, TrackerType.SMALL, 1, false, null));

        List<PetResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void shouldThrowExceptionWhenDogHasLostTracker() {
        PetRequestDto invalidDog = new PetRequestDto(PetType.DOG, TrackerType.BIG, 123, false, true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.save(invalidDog));
        assertEquals("Dogs must not have a lostTracker attribute.", ex.getMessage());
    }

    @Test
    void shouldSaveCatWithNullLostTracker() {
        PetRequestDto dto = new PetRequestDto(PetType.CAT, TrackerType.SMALL, 111, false, null);
        Cat cat = new Cat();
        cat.setOwnerId(111);
        cat.setInZone(false);
        cat.setTrackerType(TrackerType.SMALL);
        cat.setLostTracker(false); // assume default false

        PetResponseDto expected = new PetResponseDto(1L, PetType.CAT, TrackerType.SMALL, 111, false, false);

        when(mapper.toCat(dto)).thenReturn(cat);
        when(repo.save(cat)).thenReturn(cat);
        when(mapper.toResponseDto(cat)).thenReturn(expected);

        PetResponseDto result = service.save(dto);

        assertEquals(expected, result);
        verify(repo).save(cat);
    }

    @Test
    void shouldReturnSummaryItems() {
        SummaryItemDto dto = new SummaryItemDto(PetType.CAT, TrackerType.BIG, 2);
        when(repo.countOutsideGrouped()).thenReturn(List.of(dto));

        List<SummaryItemDto> result = service.summaryOutside();

        assertEquals(1, result.size());
        assertEquals(2, result.getFirst().count());
        verify(repo).countOutsideGrouped();
    }

}
