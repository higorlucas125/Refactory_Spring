package br.com.alura.adopet.api.validate;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements Validarcao {

    @Autowired
    private PetRepository petRepository;

    @Override
    public void validar(SolicitacaoAdocaoDTO solicitacaoAdocaoDTO) {
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDTO.idPet());

        if(pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }

    }
}
