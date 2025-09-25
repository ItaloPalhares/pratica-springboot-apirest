package med.voll.apiRest.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.apiRest.domain.DTO.AtualizaDadosCadastroMedico;
import med.voll.apiRest.domain.DTO.CadastroMedicoDTO;
import med.voll.apiRest.domain.endereco.Endereco;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Medico(CadastroMedicoDTO dadosCadastroMedico) {
        
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
        this.ativo = true;

    }

    public void atualizaInformacao(AtualizaDadosCadastroMedico dadosAlteracaoCadastroMedico) {
        if(dadosAlteracaoCadastroMedico.nome() != null){
            this.nome = dadosAlteracaoCadastroMedico.nome();
        }

        if(dadosAlteracaoCadastroMedico.telefone() != null){
            this.telefone = dadosAlteracaoCadastroMedico.telefone();
        }

        if(dadosAlteracaoCadastroMedico.endereco() != null){
            this.endereco.atualizaInformacao(dadosAlteracaoCadastroMedico.endereco());
        }
        
    }

    public void desativaMedicoCadastrado() {
       this.ativo = false;
    }

    

}
