package med.voll.apiRest.domain.consulta.validacoes;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {

    void validar(AgendamentoConsultaDTO dados);

}
