package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovarAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.hateos.AprovacaoAdocaoHateos;
import br.com.alura.adopet.api.dto.hateos.ReprovarAdocaoHateos;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {


    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<CollectionModel<EntityModel<?>>> solicitar(@RequestBody @Valid SolicitacaoAdocaoDTO adocao) {
        try {
            AprovacaoAdocaoHateos aprovacaoAdocaoHateos = new AprovacaoAdocaoHateos(new AprovacaoAdocaoDTO(adocao.idPet()));
            aprovacaoAdocaoHateos.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AdocaoController.class).aprovar(aprovacaoAdocaoHateos.getAprovacaoAdocaoDTO())).withSelfRel());

            ReprovarAdocaoHateos reprovarAdocaoHateos = new ReprovarAdocaoHateos(new ReprovarAdocaoDTO(adocao.idTutor(),"Adicionar motivo"));
            reprovarAdocaoHateos.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AdocaoController.class).reprovar(reprovarAdocaoHateos.getReprovarAdocaoDTO())).withSelfRel());


            this.adocaoService.solicitar(adocao);

            Supplier<CollectionModel<EntityModel<?>>> collectionModelSupplier = () -> {
                List<EntityModel<?>> entityModels = Stream.of(aprovacaoAdocaoHateos,reprovarAdocaoHateos)
                        .map(EntityModel::of)
                        .collect(Collectors.toList());
                return CollectionModel.of(entityModels);
            };

            return ResponseEntity.ok(collectionModelSupplier.get());
        } catch (ValidacaoException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(CollectionModel.of(Arrays.asList(EntityModel.of(e.getMessage()))));
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDTO adocao) {

        this.adocaoService.aprovar(adocao);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovarAdocaoDTO adocao) {
       this.adocaoService.reprovar(adocao);
        return ResponseEntity.ok().build();
    }

}
