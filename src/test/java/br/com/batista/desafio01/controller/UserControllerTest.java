package br.com.batista.desafio01.controller;


import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.repository.IUserRepository;
import br.com.batista.desafio01.service.user.UserService;
import br.com.batista.desafio01.service.usertype.IUserTypeService;
import br.com.batista.desafio01.utils.MockUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    @Autowired
    private UserService userService;

    @MockitoBean
    private IUserTypeService userTypeService;

    @MockitoBean(answers = Answers.RETURNS_DEEP_STUBS)
    IUserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Mockito.mockitoSession().initMocks(this);

    }

    @Test
    public void when_create_user_invalid_document_then_fail() throws Exception {

        UserDTO user = new UserDTO();
        user.setName("user1");
        user.setPassword("user123!@#");
        user.setEmail("user1@teste.com");
        user.setDocument("012345678910");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.document").value("CPF ou CPNJ invalido"))
                .andDo(print());
    }

    @Test
    public void when_create_user_invalid_name_then_fail() throws Exception {

        UserDTO user = new UserDTO();
        user.setName("");
        user.setPassword("user123!@#");
        user.setEmail("user1@teste.com");
        user.setDocument("94071312033");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.name").value("O nome deve ter entre 2 e 20 caracteres"))
                .andDo(print());
    }

    @Test
    public void when_create_user_invalid_password_then_fail() throws Exception {

        UserDTO user = new UserDTO();
        user.setName("user1");
        user.setPassword("");
        user.setEmail("user1@teste.com");
        user.setDocument("94071312033");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.password").value("A senha deve ter entre 6 e 12 caracteres"))
                .andDo(print());
    }

    @Test
    public void when_create_user_invalid_email_then_fail() throws Exception {

        UserDTO user = new UserDTO();
        user.setName("user1");
        user.setPassword("user123!@#");
        user.setEmail("user1teste.com");
        user.setDocument("94071312033");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.email").value("O e-mail deve ser válido"))
                .andDo(print());

    }

    @Test
    public void when_create_user_then_success() throws Exception {

        User user = MockUtils.mockUser();
        UserDTO userDTO = new UserDTO(user);

        UserType userType = MockUtils.mockUserType();
        when(userService.processDTO(userDTO)).thenReturn(user);//
        when(userTypeService.findTypeUser()).thenReturn(userType);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userDTO)))
                .andDo(print());

    }

}
