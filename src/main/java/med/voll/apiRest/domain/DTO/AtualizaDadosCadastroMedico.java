package med.voll.apiRest.domain.DTO;

import med.voll.apiRest.domain.endereco.DadosEndereco;

public record AtualizaDadosCadastroMedico(Long id, String nome, String telefone, DadosEndereco endereco) {

}
