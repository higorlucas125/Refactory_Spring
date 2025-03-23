package br.com.alura.adopet.api.dto.hateos;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import org.springframework.hateoas.RepresentationModel;

public class AprovacaoAdocaoHateos extends RepresentationModel<AprovacaoAdocaoHateos> {
    private final AprovacaoAdocaoDTO aprovacaoAdocaoDTO;

    public AprovacaoAdocaoHateos(AprovacaoAdocaoDTO aprovacaoAdocaoDTO) {
        this.aprovacaoAdocaoDTO = aprovacaoAdocaoDTO;
    }

    public AprovacaoAdocaoDTO getAprovacaoAdocaoDTO() {
        return aprovacaoAdocaoDTO;
    }

}
