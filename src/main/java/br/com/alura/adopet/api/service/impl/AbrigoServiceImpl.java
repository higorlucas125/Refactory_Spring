package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.AbrigoDTO;
import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.PetsDTO;
import br.com.alura.adopet.api.exception.CadastroException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


@Service
public class AbrigoServiceImpl implements AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;


    @Override
    public Page<AbrigoDTO> listar(Pageable pageable) {
        return abrigoRepository.findAll(pageable).map(AbrigoDTO::new);
    }

    @Override
    public void cadastrar(CadastroAbrigoDTO cadastroAbrigoDTO) {

        boolean nomeJaCadastrado = abrigoRepository.existsByNome(cadastroAbrigoDTO.nome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(cadastroAbrigoDTO.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(cadastroAbrigoDTO.email());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new CadastroException("Dados já cadastrados para outro abrigo!");
        } else {
            Abrigo abrigo = new Abrigo(cadastroAbrigoDTO.nome(), cadastroAbrigoDTO.telefone(), cadastroAbrigoDTO.email());
            abrigoRepository.save(abrigo);
        }

    }

}
