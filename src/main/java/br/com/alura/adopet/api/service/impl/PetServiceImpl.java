package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository repository;

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Override
    public Page<DadosDetalhesPet> listarTodosOsPets(Pageable pageable) {
        List<DadosDetalhesPet> dadosDetalhesPets = repository.findAllByStatus(false,pageable).stream().map(DadosDetalhesPet::new).toList();
        return new PageImpl<>(dadosDetalhesPets, pageable, repository.count());
    }

    @Override
    public List<DadosDetalhesPet> listarPets(String idOuNome) {
        return Optional.ofNullable(idOuNome).map(idOrName -> {
            try{
                Long id = Long.parseLong(idOrName);
                return abrigoRepository.getReferenceById(id).getPets().stream().map(DadosDetalhesPet::new).toList();
            }catch (NumberFormatException e){
                return abrigoRepository.findByNome(idOrName).getPets().stream().map(DadosDetalhesPet::new).toList();
            }
        }).orElseThrow(() -> new EntityNotFoundException("Abrigo não encontrado!"));
    }

    @Override
    public void cadastrarPet(String idOuNome,DadosDetalhesPet dadosDetalhesPet) {
        Optional.ofNullable(idOuNome).map( idOrName ->{
            try {
                Long id = Long.parseLong(idOrName);
                return abrigoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Abrigo não encontrado!"));
            } catch (NumberFormatException e) {
                return Optional.ofNullable(abrigoRepository.findByNome(idOuNome)).orElseThrow(() -> new EntityNotFoundException("Abrigo não encontrado!"));
            }
        }).ifPresent(abrigo -> savePetInAbrigo(dadosDetalhesPet,abrigo));

    }

    private void savePetInAbrigo(DadosDetalhesPet dadosDetalhesPet, Abrigo abrigo) {
        Pet pet = new Pet(dadosDetalhesPet);
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
        abrigoRepository.save(abrigo);
    }
}
