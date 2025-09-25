package med.voll.apiRest.domain.DTO;

import med.voll.apiRest.domain.endereco.DadosEndereco;

public record AtualizaCadastroPaciente(Long id, String nome, String telefone, DadosEndereco endereco) {

}
