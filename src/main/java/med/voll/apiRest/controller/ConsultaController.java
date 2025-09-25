package med.voll.apiRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.DTO.CancelamentoConsultaDTO;
import med.voll.apiRest.domain.DTO.DetalhesConsultaDTO;
import med.voll.apiRest.domain.consulta.AgendaDeConsulta;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsulta agenda;


    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesConsultaDTO> agendarConsulta(@RequestBody @Valid AgendamentoConsultaDTO dados){
        var dto = agenda.agendar(dados);



        return ResponseEntity.ok(dto);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancelarConsulta(@RequestBody @Valid CancelamentoConsultaDTO dados){
        agenda.cancelarConsulta(dados);
        return ResponseEntity.noContent().build();
    }

}
