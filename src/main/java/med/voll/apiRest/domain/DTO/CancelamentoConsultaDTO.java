package med.voll.apiRest.domain.DTO;

import jakarta.validation.constraints.NotNull;

public record CancelamentoConsultaDTO(
    @NotNull
    long idConsulta,

    @NotNull
    MotivoCancelamento motivoCancelamento


) {

}
