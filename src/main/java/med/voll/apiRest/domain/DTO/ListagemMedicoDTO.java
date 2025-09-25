package med.voll.apiRest.domain.DTO;

import med.voll.apiRest.domain.medico.Especialidade;
import med.voll.apiRest.domain.medico.Medico;

public record ListagemMedicoDTO(Long id, String nome, String email, String crm, Especialidade especialidade, Boolean ativo) {

    public ListagemMedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getAtivo());
    }

}
