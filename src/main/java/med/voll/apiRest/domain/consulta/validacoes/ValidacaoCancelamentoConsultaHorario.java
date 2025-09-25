package med.voll.apiRest.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.CancelamentoConsultaDTO;
import med.voll.apiRest.domain.repository.ConsultaRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidacaoCancelamentoConsultaHorario implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(CancelamentoConsultaDTO dados) {
        var dataConsulta = consultaRepository.getReferenceById(dados.idConsulta()).getData();
        var agora = LocalDateTime.now();
        var intervalo = Duration.between(agora, dataConsulta).toHours();

        if(intervalo < 24){
            throw new ValidacaoException("A consulta só pode cancelada com até 24 horas de antecedência!");
        }
    }

}
