package med.voll.apiRest.domain.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.apiRest.domain.medico.Especialidade;

public record AgendamentoConsultaDTO(
    Long idMedico,

    @NotNull
    Long idPaciente,

    @NotNull
    @Future
    LocalDateTime data,

    Especialidade especialidade
) {

}
