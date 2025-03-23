package br.com.alura.adopet.api.dto.hateos;

import br.com.alura.adopet.api.dto.ReprovarAdocaoDTO;
import org.springframework.hateoas.RepresentationModel;

public class ReprovarAdocaoHateos extends RepresentationModel<ReprovarAdocaoHateos> {
    private final ReprovarAdocaoDTO reprovarAdocaoDTO;

    public ReprovarAdocaoHateos(ReprovarAdocaoDTO reprovarAdocaoDTO) {
        this.reprovarAdocaoDTO = reprovarAdocaoDTO;
    }

    public ReprovarAdocaoDTO getReprovarAdocaoDTO() {
        return reprovarAdocaoDTO;
    }
}
