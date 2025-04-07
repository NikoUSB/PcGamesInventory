package com.segundocorte.inventario.Services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.segundocorte.inventario.Repositories.PcGamesRepository;
import com.segundocorte.inventario.Entities.PcGamesEntity;

import java.util.*;

@Service
@AllArgsConstructor
public class PcGamesService {

    private final PcGamesRepository pcGamesRepository;

    public ResponseEntity<?> getAllPcGames(Pageable pageable) {
        Page<PcGamesEntity> games = pcGamesRepository.findAll(pageable);
        return getResponseEntity(games);
    }

    public ResponseEntity<?> getPcGameById(UUID id) {
        Optional<PcGamesEntity> game = pcGamesRepository.findById(id);
        if (game.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", String.format("Game with ID %s not found", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(Collections.singletonMap("Game", game.get()));
    }

    public ResponseEntity<?> getPcGameByName(String GameName, Pageable pageable) {
        Page<PcGamesEntity> games = pcGamesRepository.findAllByGameNameContaining(GameName, pageable);
        return getResponseEntity(games);
    }

    private ResponseEntity<?> getResponseEntity(Page<PcGamesEntity> games) {
        Map<String, Object> response = new HashMap<>();
        response.put("TotalElements", games.getTotalElements());
        response.put("TotalPages", games.getTotalPages());
        response.put("CurrentPage", games.getNumber());
        response.put("NumberOfElements", games.getNumberOfElements());
        response.put("Games", games.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addGame(PcGamesEntity gameToAdd) {
        Page<PcGamesEntity> game = pcGamesRepository.findAllByGameNameContaining(
                gameToAdd.getGameName(),
                Pageable.unpaged());
        if (game.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Game already exists with %d coincidences.", game.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            PcGamesEntity savedGame = pcGamesRepository.save(gameToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Added Game with ID %s", savedGame.getId())), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> updateGame(UUID id, PcGamesEntity gameToUpdate) {
        Optional<PcGamesEntity> game = pcGamesRepository.findById(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Game with ID %s not found.", id)), HttpStatus.NOT_FOUND);
        }
        PcGamesEntity existingMovie = game.get();

        existingMovie.setGameName(gameToUpdate.getGameName());
        existingMovie.setGameYear(gameToUpdate.getGameYear());
        existingMovie.setGameScore(gameToUpdate.getGameScore());
        existingMovie.setGameDev(gameToUpdate.getGameDev());

        pcGamesRepository.save(existingMovie);

        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Updated Game with ID %s", existingMovie.getId())));
    }

    public ResponseEntity<?> deleteGame(UUID id) {
        Optional<PcGamesEntity> game = pcGamesRepository.findById(id);
        if (game.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Game with ID %s doesn't exist.", id)),HttpStatus.NOT_FOUND);
        }
        pcGamesRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Deleted Game with ID %s", id)));
    }

}
