package com.nipuna.tractive.pettracker.web;

import com.nipuna.tractive.pettracker.dto.PetRequestDto;
import com.nipuna.tractive.pettracker.dto.PetResponseDto;
import com.nipuna.tractive.pettracker.dto.SummaryItemDto;
import com.nipuna.tractive.pettracker.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService svc;

    @PostMapping
    public ResponseEntity<PetResponseDto> create(@Valid @RequestBody PetRequestDto d) {
        return new ResponseEntity<>(svc.save(d), CREATED);
    }

    @GetMapping
    public List<PetResponseDto> list() {
        return svc.findAll();
    }

    @GetMapping("/summary")
    public List<SummaryItemDto> summary() {
        return svc.summaryOutside();
    }
}
