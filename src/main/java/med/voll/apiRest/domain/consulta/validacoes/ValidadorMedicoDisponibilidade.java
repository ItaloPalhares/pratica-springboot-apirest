package med.voll.apiRest.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.repository.ConsultaRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorMedicoDisponibilidade implements ValidadorAgendamentoDeConsulta {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(AgendamentoConsultaDTO dados) {
        var medicoDisponibilidade = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if(medicoDisponibilidade){
            throw new ValidacaoException("Médico selecionado não está disponivel nesse horário!");
        }


    }

}
