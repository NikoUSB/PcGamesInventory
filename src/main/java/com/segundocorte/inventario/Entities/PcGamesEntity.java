package com.segundocorte.inventario.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Entity
@Table(name = "PC_GAMES_ENTITY")
@AllArgsConstructor
@NoArgsConstructor
public class PcGamesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @Setter
    @Column(name = "pc_game_name", nullable = false, length = 255)
    @JsonProperty("GameName")
    @NotBlank(message = "Game name is required")
    @Size(min = 3, max = 100, message = "Game Name must be between 3 and 100 characters")
    private String gameName;

    @Setter
    @Column(name = "pc_game_year", nullable = false, length = 4)
    @JsonProperty("GameYear")
    @NotBlank(message = "Year is required")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be a valid 4-digit number (1900-2099)")
    private String gameYear;

    @Setter
    @Column(name = "pc_game_score", nullable = false)
    @JsonProperty("GameScore")
    @NotNull(message = "Meta Critic score is required")
    @Max(value = 100, message = "The Game Score must be between 0 and 100")
    private int gameScore;

    @Setter
    @Column(name = "pc_game_dev", nullable = false, length = 255)
    @JsonProperty("GameDev")
    @NotBlank(message = "Developer is required")
    @Size(min = 2, message = "Developer name must be at least 2 characters")
    private String gameDev;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "PcGamesEntity{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", gameYear='" + gameYear + '\'' +
                ", gameScore=" + gameScore +
                ", gameDev='" + gameDev + '\'' +
                '}';
    }
}
