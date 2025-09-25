package med.voll.apiRest.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.repository.ConsultaRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorPacienteDisponibilidade implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(AgendamentoConsultaDTO dados) {
        var primeiroHorario = dados.data().withHour(7);
        var segundoHorario = dados.data().withHour(18);
        var pacienteTemConsultaAgendadaNoMesmoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, segundoHorario);
        if(pacienteTemConsultaAgendadaNoMesmoDia){
            throw new ValidacaoException("Paciente já possui consulta agendada para essa data!");
        }
    }

}
