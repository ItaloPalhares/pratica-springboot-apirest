package med.voll.apiRest.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.repository.MedicoRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Component
public class ValidadorAtivoMedico implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(AgendamentoConsultaDTO dados) {

        //caso um medico especifico não seja selecionado
        if(dados.idMedico() == null){
            return;
        }
       
        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if(!medicoEstaAtivo){
            throw new  ValidacaoException("Medico escolhido não está ativo no sistema!");
        }
    }

}
