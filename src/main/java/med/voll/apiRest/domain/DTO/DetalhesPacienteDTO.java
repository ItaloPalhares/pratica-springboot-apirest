package med.voll.apiRest.domain.DTO;

import med.voll.apiRest.domain.endereco.Endereco;
import med.voll.apiRest.domain.paciente.Paciente;

public record DetalhesPacienteDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String cpf,
    Endereco endereco) {

        public DetalhesPacienteDTO(Paciente paciente){
            this(paciente.getId(),
            paciente.getNome(),
            paciente.getEmail(),
            paciente.getTelefone(),
            paciente.getCpf(),
            paciente.getEndereco());
        }


}
