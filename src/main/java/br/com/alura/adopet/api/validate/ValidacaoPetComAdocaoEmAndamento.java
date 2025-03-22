package br.com.alura.adopet.api.validate;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.enums.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements Validarcao {

    @Autowired
    private AdocaoRepository adocaoRepository;


    @Override
    public void validar(SolicitacaoAdocaoDTO solicitacaoAdocaoDTO) {
        boolean petTemAdocaoEmAndamento = adocaoRepository.existsByPetIdAndStatus(solicitacaoAdocaoDTO.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);
        if (petTemAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }

    }
}
