package med.voll.apiRest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

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

import med.voll.apiRest.domain.DTO.AgendamentoConsultaDTO;
import med.voll.apiRest.domain.DTO.DetalhesConsultaDTO;
import med.voll.apiRest.domain.consulta.AgendaDeConsulta;
import med.voll.apiRest.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DetalhesConsultaDTO> detalhesConsultaDTOJSON;

    @Autowired
    private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaDTOJSON;

    @SuppressWarnings("removal")
    @MockBean
    private AgendaDeConsulta agendaDeConsultas;



    @Test
    @DisplayName("Devolver Código 400 quando infos forem inválidas")
    @WithMockUser
    void agendarConsultaCodigo400() throws Exception{

       var response =  mvc.perform(post("/consultas"))
        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    @DisplayName("Devolver Código 200 quando infos forem válidas")
    @WithMockUser
    void agendarConsultaCodigo200() throws Exception{

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA; 
        var detalhesDTO =  new DetalhesConsultaDTO(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(detalhesDTO);

       var response =  mvc.perform(post("/consultas")
       .contentType(MediaType.APPLICATION_JSON)
        .content(agendamentoConsultaDTOJSON.write(new AgendamentoConsultaDTO(2l, 5l, data, especialidade)
        ).getJson()
        ))
        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalhesConsultaDTOJSON.write(detalhesDTO).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}