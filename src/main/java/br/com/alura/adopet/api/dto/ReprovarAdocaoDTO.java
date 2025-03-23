package br.com.alura.adopet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReprovarAdocaoDTO(@JsonProperty @NotNull Long idAdocao, @JsonProperty @NotBlank String justificativa) {
}
