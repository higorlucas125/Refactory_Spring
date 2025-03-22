package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.enums.TipoPet;

public record PetsDTO(TipoPet tipoPet, String nome, String raca, Integer idade, String cor, Float peso, Boolean adotado) {

    public PetsDTO (Pet pet) {
        this(pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso(), pet.getAdotado());
    }
}
