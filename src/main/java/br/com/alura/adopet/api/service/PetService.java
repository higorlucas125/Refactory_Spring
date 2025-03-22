package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetService {

    Page<DadosDetalhesPet> listarTodosOsPets(Pageable pageable);
    List<DadosDetalhesPet> listarPets(String idOuNome);
    void cadastrarPet(String idOuNome,DadosDetalhesPet pet);
}
