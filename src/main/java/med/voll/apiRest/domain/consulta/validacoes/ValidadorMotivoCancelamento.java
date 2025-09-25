package med.voll.apiRest.domain.consulta.validacoes;

import med.voll.apiRest.domain.DTO.CancelamentoConsultaDTO;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

public class ValidadorMotivoCancelamento implements ValidadorCancelamentoDeConsulta {

    @Override
    public void validar(CancelamentoConsultaDTO dados) {
        if(dados.motivoCancelamento() == null){
            throw new ValidacaoException("Campo Motivo de cancelamento não pode estar vazio");
        }
    }

}
