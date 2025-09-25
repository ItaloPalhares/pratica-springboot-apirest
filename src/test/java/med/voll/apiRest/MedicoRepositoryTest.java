package med.voll.apiRest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.apiRest.domain.DTO.CadastroMedicoDTO;
import med.voll.apiRest.domain.DTO.CadastroPacienteDTO;
import med.voll.apiRest.domain.consulta.Consulta;
import med.voll.apiRest.domain.endereco.DadosEndereco;
import med.voll.apiRest.domain.medico.Especialidade;
import med.voll.apiRest.domain.medico.Medico;
import med.voll.apiRest.domain.paciente.Paciente;
import med.voll.apiRest.domain.repository.MedicoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolver null quando o único medico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario01(){
        var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);



        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();

    }



    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario02(){
        var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
      
        
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);

    }



    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));

}

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade ) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
}

    private CadastroMedicoDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new CadastroMedicoDTO(
            nome,
            email,
            "61999999999",
            crm,
            especialidade,
            dadosEndereco()
    );
}

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    em.persist(paciente);
    return paciente;
}

    private CadastroPacienteDTO dadosPaciente(String nome, String email, String cpf) {
    return new CadastroPacienteDTO(
            nome,
            email,
            "61999999999",
            cpf,
            dadosEndereco()
    );
}

    private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
            "rua xpto",
            "bairro",
            "00000000",
            "Brasilia",
            "DF",
            null,
            null
    );
}


}
