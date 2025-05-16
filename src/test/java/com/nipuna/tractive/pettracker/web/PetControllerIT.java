package com.nipuna.tractive.pettracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nipuna.tractive.pettracker.PetTrackerApplication;
import com.nipuna.tractive.pettracker.dto.PetRequestDto;
import com.nipuna.tractive.pettracker.domain.PetType;
import com.nipuna.tractive.pettracker.domain.TrackerType;
import com.nipuna.tractive.pettracker.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PetTrackerApplication.class)
@AutoConfigureMockMvc
class PetControllerIT {
    private static final String PETS_BASE = "/api/v1/pets";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private PetRepository repo;

    @BeforeEach
    void setup() {
        repo.deleteAll();
    }

    @Test
    void testCreateCatWithLostTracker() throws Exception {
        PetRequestDto request = new PetRequestDto(PetType.CAT, TrackerType.SMALL, 111, false, true);

        mvc.perform(post(PETS_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petType").value("CAT"))
                .andExpect(jsonPath("$.lostTracker").value(true));
    }

    @Test
    void testCreateDogWithoutLostTracker() throws Exception {
        PetRequestDto request = new PetRequestDto(PetType.DOG, TrackerType.MEDIUM, 222, true, null);

        mvc.perform(post(PETS_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petType").value("DOG"))
                .andExpect(jsonPath("$.lostTracker").doesNotExist());
    }

    @Test
    void getAllPetsReturnsCorrectList() throws Exception {
        PetRequestDto cat = new PetRequestDto(PetType.CAT, TrackerType.BIG, 111, false, false);
        PetRequestDto dog = new PetRequestDto(PetType.DOG, TrackerType.MEDIUM, 222, true, null);

        mvc.perform(post(PETS_BASE).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated());
        mvc.perform(post(PETS_BASE).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isCreated());

        mvc.perform(get(PETS_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void summaryReturnsCorrectCountForDog() throws Exception {
        PetRequestDto dog = new PetRequestDto(PetType.DOG, TrackerType.SMALL, 1, false, null);
        mvc.perform(post(PETS_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isCreated());

        mvc.perform(get(PETS_BASE + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].petType").value("DOG"))
                .andExpect(jsonPath("$[0].trackerType").value("SMALL"))
                .andExpect(jsonPath("$[0].count").value(1));
    }

    @Test
    void summaryReturnsCorrectCountForCat() throws Exception {
        PetRequestDto cat = new PetRequestDto(PetType.CAT, TrackerType.SMALL, 1, false, true);
        mvc.perform(post(PETS_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated());

        mvc.perform(get(PETS_BASE + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].petType").value("CAT"))
                .andExpect(jsonPath("$[0].trackerType").value("SMALL"))
                .andExpect(jsonPath("$[0].count").value(1));
    }

    @Test
    void summaryExcludesInZonePets() throws Exception {
        PetRequestDto outsideCat = new PetRequestDto(PetType.CAT, TrackerType.BIG, 2, false, true);
        PetRequestDto insideDog = new PetRequestDto(PetType.DOG, TrackerType.MEDIUM, 3, true, null);

        mvc.perform(post(PETS_BASE).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(outsideCat)))
                .andExpect(status().isCreated());

        mvc.perform(post(PETS_BASE).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insideDog)))
                .andExpect(status().isCreated());

        mvc.perform(get(PETS_BASE + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].petType").value("CAT"))
                .andExpect(jsonPath("$[0].trackerType").value("BIG"))
                .andExpect(jsonPath("$[0].count").value(1));
    }

    @Test
    void summaryReturnsEmptyListWhenNoPetsExist() throws Exception {
        mvc.perform(get(PETS_BASE + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testValidationErrorOnMissingFields() throws Exception {
        String invalidJson = """
                {
                    "inZone": false,
                    "ownerId": 333
                }
                """;

        mvc.perform(post(PETS_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
