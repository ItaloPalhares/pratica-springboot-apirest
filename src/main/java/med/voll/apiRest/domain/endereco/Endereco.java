package med.voll.apiRest.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

     public Endereco(DadosEndereco endereco) {
        this.logradouro =  endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
    }

     public void atualizaInformacao(DadosEndereco novoDadoEndereco) {
        if(novoDadoEndereco.logradouro() != null){
            this.logradouro = novoDadoEndereco.logradouro();
        }

         if(novoDadoEndereco.bairro() != null){
            this.bairro = novoDadoEndereco.bairro();
        }

         if(novoDadoEndereco.cep() != null){
            this.cep = novoDadoEndereco.cep();
        }

         if(novoDadoEndereco.numero() != null){
            this.numero = novoDadoEndereco.numero();
        }

         if(novoDadoEndereco.complemento() != null){
            this.complemento = novoDadoEndereco.complemento();
        }

         if(novoDadoEndereco.cidade() != null){
            this.cidade = novoDadoEndereco.cidade();
        }

         if(novoDadoEndereco.uf() != null){
            this.uf = novoDadoEndereco.uf();
        }
     }
}
