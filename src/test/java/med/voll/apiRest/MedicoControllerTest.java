package med.voll.apiRest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.util.ReflectionTestUtils;

import med.voll.apiRest.domain.DTO.AtualizaDadosCadastroMedico;
import med.voll.apiRest.domain.DTO.CadastroMedicoDTO;
import med.voll.apiRest.domain.DTO.DetalhesMedicoDTO;
import med.voll.apiRest.domain.endereco.DadosEndereco;
import med.voll.apiRest.domain.medico.Especialidade;
import med.voll.apiRest.domain.medico.Medico;
import med.voll.apiRest.domain.repository.MedicoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroMedicoDTO> cadastroMedicoDTOJSON;

    @Autowired
    private JacksonTester<DetalhesMedicoDTO> detalhesMedicoDTOJSON;

    @Autowired
    private JacksonTester<AtualizaDadosCadastroMedico> atualizaDadosCadastroMedicoDTOJSON;


    @SuppressWarnings("removal")
    @MockBean
    private MedicoRepository repository;

  




    @Test
    @DisplayName("Devolver Código 400 quando infos forem inválidas")
    @WithMockUser
    void postCadastrarMedico_Cenario01() throws Exception{
        var response =  mvc.perform(post("/medicos"))
        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


    }

    @Test
    @DisplayName("Devolver código 200 quando as infos forem válidas")
    @WithMockUser
    void postCadastrarMedico_Cenario02() throws Exception{
        
        var endereco = new DadosEndereco("rua 5", "Pera", "123456789", "9", "casa 2", "Rio de Janeiro", "RJ");

        var especialidade = Especialidade.DERMATOLOGIA;

        var cadastroMedico = new CadastroMedicoDTO("pablo", "pablo@voll.med", "123123123", "654321", especialidade, endereco);

        var medico = new Medico(cadastroMedico);

        var detalhesMedicoDTO = new DetalhesMedicoDTO(medico);

        

        var response =  mvc.perform(post("/medicos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(cadastroMedicoDTOJSON.write(cadastroMedico)
        .getJson())
        )
        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = detalhesMedicoDTOJSON.write(detalhesMedicoDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }


    @Test
    @DisplayName("Devolver código 200 quando as infos forem válidas")
    @WithMockUser
    void atualizarCadastroMedico_Cenario01() throws Exception{
        //arrange
        var endereco = new DadosEndereco("rua 5", "Pera", "123456789", "9", "casa 2", "Rio de Janeiro", "RJ");
        var especialidade = Especialidade.DERMATOLOGIA;
        var cadastroMedico = new CadastroMedicoDTO("pablo", "pablo@voll.med", "123123123", "654321", especialidade, endereco);
        var medico = new Medico(cadastroMedico);

        //instrui o mockito
        ReflectionTestUtils.setField(medico, "id", 1L);
        when(repository.save(any(Medico.class))).thenReturn(medico);
        when(repository.getReferenceById(1L)).thenReturn(medico);

        //  as inforamções que vão tomar o lugar das antigas
        var dadosCadastroMedico = new AtualizaDadosCadastroMedico( 1L, "roberto","999999999", endereco );

        //fazendo o mcv rodar uma chamada put falsa pra alterar um medico com os dados q mandei acima
        var response = mvc.perform(
            put("/medicos") 
            .contentType(MediaType.APPLICATION_JSON)
            .content(atualizaDadosCadastroMedicoDTOJSON.write(dadosCadastroMedico).getJson()))
            .andReturn().getResponse();


        //checando se deu certo e também se o getreference foi usado.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(repository).getReferenceById(1L);
        
        var jsonEsperado = detalhesMedicoDTOJSON.write(new DetalhesMedicoDTO(medico)).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

        assertThat(response.getContentAsString()).contains("roberto");



        

    }


}
