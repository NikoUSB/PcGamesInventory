package com.segundocorte.inventario.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PcGamesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @Setter
    @JsonProperty("GameName")
    @NotBlank(message = "Game name is required")
    @Size (min = 3, max = 100, message = "Game Name must be between 3 and 100 characters")
    private String gameName;

    @Setter
    @JsonProperty("GameYear")
    @NotBlank(message = "Year is required")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be a valid 4-digit number (1900-2099)")
    private String GameYear;

    @Setter
    @JsonProperty("GameScore")
    @NotNull(message = "Meta Critic score is required")
    @Max(value = 100, message = "The Game Score must be between 0 anr 100")
    private int GameScore;

    @Setter
    @JsonProperty("GameDev")
    @NotBlank(message = "Developer is required")
    @Min(value = 2, message = "Developer name must be least 2 characters")
    private String GameDev;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "PCGamesEntity{" +
                "id=" + id +
                ", GameName='" + gameName + '\'' +
                ", GameYear='" + GameYear + '\'' +
                ", GameScore='" + GameScore + '\'' +
                ", GameDeveloper=" + GameDev +
                '}';
    }

}
