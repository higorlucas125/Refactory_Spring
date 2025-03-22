package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.dto.PetsDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhesPet>> listarTodosDisponiveis(Pageable pageable) {
        return ResponseEntity.ok(petService.listarTodosOsPets(pageable));
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<?> listarPets(@PathVariable String idOuNome) {
        try{
            return ResponseEntity.ok(petService.listarPets(idOuNome));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid DadosDetalhesPet pet) {
        try {
            petService.cadastrarPet(idOuNome, pet);
            return ResponseEntity.ok("Pet cadastrado com sucesso!");
        } catch ( EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
