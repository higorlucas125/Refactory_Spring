package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovarAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;

public interface AdocaoService {
     void solicitar(SolicitacaoAdocaoDTO adocao) throws ValidacaoException;
     void aprovar(AprovacaoAdocaoDTO adocao);
     void reprovar(ReprovarAdocaoDTO adocao);
}
