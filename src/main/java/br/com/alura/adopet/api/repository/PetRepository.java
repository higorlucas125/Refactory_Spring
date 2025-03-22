package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.adocao = :status")
    Page<Pet> findAllByStatus(boolean status, Pageable pageable);

}
