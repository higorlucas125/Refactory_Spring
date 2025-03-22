package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AbrigoDTO(@NotBlank String nome, @NotBlank String telefone, @NotBlank String email, List<PetsDTO> petsDTOS) {

    public AbrigoDTO(Abrigo abrigo){
        this(abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail(), abrigo.getPets().stream().map(PetsDTO::new).toList());
    }
}
