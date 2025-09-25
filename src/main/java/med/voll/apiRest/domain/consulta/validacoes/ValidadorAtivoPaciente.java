package med.voll.apiRest.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.repository.PacienteRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorAtivoPaciente implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(AgendamentoConsultaDTO dados) {
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if(!pacienteEstaAtivo){
            throw new ValidacaoException("Paciente não está ativo no sistema!");
        }
    }

}
