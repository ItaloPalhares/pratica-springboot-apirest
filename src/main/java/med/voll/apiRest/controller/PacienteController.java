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
import med.voll.apiRest.domain.DTO.AtualizaCadastroPaciente;
import med.voll.apiRest.domain.DTO.CadastroPacienteDTO;
import med.voll.apiRest.domain.DTO.DetalhesPacienteDTO;
import med.voll.apiRest.domain.DTO.ListagemPacienteDTO;
import med.voll.apiRest.domain.paciente.Paciente;
import med.voll.apiRest.domain.repository.PacienteRepository;

@RestController
@RequestMapping("paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesPacienteDTO> postCadastrarPaciente(@RequestBody @Valid CadastroPacienteDTO dadosCadastroPaciente, UriComponentsBuilder uriBuilder){
        
        var paciente = new Paciente(dadosCadastroPaciente);
        repository.save(paciente);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesPacienteDTO(paciente));
    }


    @GetMapping
    public ResponseEntity<Page <ListagemPacienteDTO>> getListaPaciente(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(ListagemPacienteDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhesPacienteDTO> atualizarCadastroPaciente(@RequestBody @Valid AtualizaCadastroPaciente dadosAlteracaoCadastroPaciente){
        var paciente  = repository.getReferenceById(dadosAlteracaoCadastroPaciente.id());
        paciente.atualizaInformacao(dadosAlteracaoCadastroPaciente);
        return ResponseEntity.ok(new DetalhesPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> desativarPacienteCadastrado(@PathVariable long id){
        var paciente = repository.getReferenceById(id);

        paciente.desativaPacienteCadastrado();

        return ResponseEntity.noContent().build();
    }
}



