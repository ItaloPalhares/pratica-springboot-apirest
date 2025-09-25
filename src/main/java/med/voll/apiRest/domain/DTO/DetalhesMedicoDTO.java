package med.voll.apiRest.domain.DTO;

import med.voll.apiRest.domain.endereco.Endereco;
import med.voll.apiRest.domain.medico.Especialidade;
import med.voll.apiRest.domain.medico.Medico;

public record DetalhesMedicoDTO(
    Long id,
    String nome,
    String email,
    String telefone, 
    String crm,
    Especialidade especialidade, 
    Endereco endereco ) {

        public DetalhesMedicoDTO(Medico medico){
            this(medico.getId(), 
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco());
        }

}
