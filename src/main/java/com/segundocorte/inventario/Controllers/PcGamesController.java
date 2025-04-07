package com.segundocorte.inventario.Controllers;

import com.segundocorte.inventario.Entities.PcGamesEntity;
import com.segundocorte.inventario.Services.PcGamesService;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/pc_games")
@Validated
public class PcGamesController {

    private final PcGamesService pcGamesService;

    public PcGamesController(PcGamesService pcGamesService) {
        this.pcGamesService = pcGamesService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCarsMovies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "GameName,desc") String[] sort){
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
            return pcGamesService.getAllPcGames(pageable);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid sorting direction. Use 'asc' or 'desc'.");
        }

    }

    private Sort.Order parseSort(String[] sort) {
        if (sort.length < 2) {
            throw new IllegalArgumentException("Sort parameter must have both field and direction (e.g., 'PcGamesYear,desc').");
        }

        String property = sort[0];
        String direction = sort[1].toLowerCase();

        List<String> validDirections = Arrays.asList("asc", "desc");
        if (!validDirections.contains(direction)) {
            throw new IllegalArgumentException("Invalid sort direction. Use 'asc' or 'desc'.");
        }

        return new Sort.Order(Sort.Direction.fromString(direction), property);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarsMovieById(@PathVariable UUID id){
        return pcGamesService.getPcGameById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getMoviesByName(
            @RequestParam String gameName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "GameName,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return pcGamesService.getPcGameByName(gameName, pageable);
    }

    @PostMapping
    public ResponseEntity<?> insertCarsMovie(@Valid @RequestBody PcGamesEntity pcGamesEntity){
        return pcGamesService.addGame(pcGamesEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarsMovie(@PathVariable UUID id, @Valid @RequestBody PcGamesEntity pcGamesEntity){
        return pcGamesService.updateGame(id,pcGamesEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarsMovie(@PathVariable UUID id){
        return pcGamesService.deleteGame(id);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

}
