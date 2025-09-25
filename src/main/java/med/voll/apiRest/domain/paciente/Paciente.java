package med.voll.apiRest.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.apiRest.domain.DTO.AtualizaCadastroPaciente;
import med.voll.apiRest.domain.DTO.CadastroPacienteDTO;
import med.voll.apiRest.domain.endereco.Endereco;


@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;


    public Paciente (CadastroPacienteDTO dadosCadastroPaciente){
        this.nome = dadosCadastroPaciente.nome();
        this.email = dadosCadastroPaciente.email();
        this.telefone = dadosCadastroPaciente.telefone();
        this.cpf = dadosCadastroPaciente.cpf();
        this.endereco = new Endereco(dadosCadastroPaciente.endereco());
        this.ativo = true;

    }


    public void atualizaInformacao(AtualizaCadastroPaciente dadosAlteracaoCadastroPaciente) {
        if(dadosAlteracaoCadastroPaciente.nome() != null){
            this.nome = dadosAlteracaoCadastroPaciente.nome();
        }

        if(dadosAlteracaoCadastroPaciente.telefone() != null){
            this.telefone = dadosAlteracaoCadastroPaciente.telefone();
        }

        if(dadosAlteracaoCadastroPaciente.endereco() != null){
            this.endereco.atualizaInformacao(dadosAlteracaoCadastroPaciente.endereco());
        };
    }


    public void desativaPacienteCadastrado() {
        this.ativo=false;
    }



}
