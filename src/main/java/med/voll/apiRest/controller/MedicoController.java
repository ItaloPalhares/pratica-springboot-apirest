package med.voll.apiRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.apiRest.domain.DTO.AtualizaDadosCadastroMedico;
import med.voll.apiRest.domain.DTO.CadastroMedicoDTO;
import med.voll.apiRest.domain.DTO.DetalhesMedicoDTO;
import med.voll.apiRest.domain.DTO.ListagemMedicoDTO;
import med.voll.apiRest.domain.medico.Medico;
import med.voll.apiRest.domain.repository.MedicoRepository;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesMedicoDTO> postCadastrarMedico(@RequestBody @Valid CadastroMedicoDTO dadosCadastroMedico,
            UriComponentsBuilder uriBuilder) {

        var medico = new Medico(dadosCadastroMedico);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesMedicoDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMedicoDTO>> getListaMedico(
            @PageableDefault(size = 5, sort = { "nome" }) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListagemMedicoDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesMedicoDTO> getMedicoPorId(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        if (medico.getAtivo() == false) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new DetalhesMedicoDTO(medico));
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhesMedicoDTO> atualizarCadastroMedico( @RequestBody @Valid AtualizaDadosCadastroMedico dadosAlteracaoCadastroMedico) {
        var medico = repository.getReferenceById(dadosAlteracaoCadastroMedico.id());
        medico.atualizaInformacao(dadosAlteracaoCadastroMedico);
        return ResponseEntity.ok(new DetalhesMedicoDTO(medico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> desativarMedicoCadastrado(@PathVariable long id) {
        var medico = repository.getReferenceById(id);

        medico.desativaMedicoCadastrado();

        return ResponseEntity.noContent().build();
    }

}
