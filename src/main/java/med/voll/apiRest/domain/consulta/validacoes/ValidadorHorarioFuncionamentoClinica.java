package med.voll.apiRest.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{

    @Override
    public void validar(AgendamentoConsultaDTO dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDoHorarioComercial = dataConsulta.getHour() < 7;
        var depoisDoHorarioComercial = dataConsulta.getHour() > 18;
        
        if(domingo || antesDoHorarioComercial || depoisDoHorarioComercial){
            throw new ValidacaoException("consulta fora do horário ou datas de funcionamento da clinica!");
        }

    }

}
