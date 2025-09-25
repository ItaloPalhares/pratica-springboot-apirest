package med.voll.apiRest.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.DTO.CancelamentoConsultaDTO;
import med.voll.apiRest.domain.DTO.DetalhesConsultaDTO;
import med.voll.apiRest.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.apiRest.domain.consulta.validacoes.ValidadorCancelamentoDeConsulta;
import med.voll.apiRest.domain.medico.Medico;
import med.voll.apiRest.domain.repository.ConsultaRepository;
import med.voll.apiRest.domain.repository.MedicoRepository;
import med.voll.apiRest.domain.repository.PacienteRepository;
import med.voll.apiRest.infra.exceptions.ValidacaoException;

@Service
public class AgendaDeConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DetalhesConsultaDTO agendar(AgendamentoConsultaDTO dados){
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("id do paciente não existe!");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("id do medico não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if( medico == null){
            throw new ValidacaoException("Sem médicos disponíveis nessa data!");
        }
        
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DetalhesConsultaDTO(consulta);

    }



    private Medico escolherMedico(AgendamentoConsultaDTO dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("o campo 'especialidade' é obrigatório quando um médico não é selecionado!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }



    public void cancelarConsulta(CancelamentoConsultaDTO dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta =  consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
        
    }

}
