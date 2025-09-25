package med.voll.apiRest.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    @Override
    public void validar(AgendamentoConsultaDTO dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(dataConsulta, agora).toMinutes();

        if(diferencaEmMinutos > 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência minima de 30 minutos!");
        }
    }

}
