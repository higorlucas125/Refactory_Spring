package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovarAdocaoDTO;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.AdocaoService;
import br.com.alura.adopet.api.service.EmailService;
import br.com.alura.adopet.api.validate.Validarcao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoServiceImpl implements AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<Validarcao> validarcaos;

    private String to;
    private String subject;
    private String message;


    @Override
    public void solicitar(SolicitacaoAdocaoDTO adocao) throws ValidacaoException {
        Pet pet = petRepository.getReferenceById(adocao.idPet());
        Tutor tutor = tutorRepository.getReferenceById(adocao.idTutor());

        //chamar as validacaoes chain of responsiblity
        validarcaos.forEach(validarcao -> {
            validarcao.validar(adocao);
        });

        Adocao adocao1 = new Adocao(tutor,pet, adocao.motivo());


        repository.save(adocao1);

        this.to = pet.getAbrigo().getEmail();
        this.subject = "Solicitação de adoção";
        this.message = "Olá " + pet.getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao1.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.";

        emailService.enviarEmail(this.to, this.subject, this.message);

    }

    @Override
    public void aprovar(AprovacaoAdocaoDTO adocao) {

        Adocao adocao1 = repository.getReferenceById(adocao.idAdocao());
        adocao1.marcarComoAprovado();
        repository.save(adocao1);

        this.to = adocao1.getTutor().getEmail();
        this.subject = "Adoção aprovada";
        this.message = "Parabéns " + adocao1.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao1.getPet().getNome() + ", solicitada em " + adocao1.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao1.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.";

        emailService.enviarEmail(this.to, this.subject, this.message);

    }

    @Override
    public void reprovar(ReprovarAdocaoDTO adocao) {
        Adocao adocao1 = repository.getReferenceById(adocao.idAdocao());
        adocao1.marcarComoReprovado(adocao.justificativa());
        repository.save(adocao1);

        this.to = adocao1.getTutor().getEmail();
        this.subject = "Adoção aprovada";
        this.message = "Olá " + adocao1.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao1.getPet().getNome() + ", solicitada em " + adocao1.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao1.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.justificativa();

        emailService.enviarEmail(this.to, this.subject, this.message);

    }
}
