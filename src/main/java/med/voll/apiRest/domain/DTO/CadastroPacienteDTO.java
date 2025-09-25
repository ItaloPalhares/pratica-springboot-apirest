package med.voll.apiRest.domain.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.apiRest.domain.endereco.DadosEndereco;

public record CadastroPacienteDTO(
   @NotBlank
    String nome,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String telefone,
    String cpf,
    @NotNull
    @Valid
    DadosEndereco endereco) {

}
