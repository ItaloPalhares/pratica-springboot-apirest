package med.voll.apiRest.domain.DTO;

import java.time.LocalDateTime;

import med.voll.apiRest.domain.consulta.Consulta;

public record DetalhesConsultaDTO(Long id, Long idMedico, Long idPaciente, LocalDateTime data ) {

    public DetalhesConsultaDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}
