package br.com.batista.desafio01.controller;


import br.com.batista.desafio01.model.dto.AuthorizeDTO;
import br.com.batista.desafio01.model.dto.TransactionDTO;
import br.com.batista.desafio01.model.entities.Transaction;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.repository.ITransactionRepository;
import br.com.batista.desafio01.service.transactionauth.ITransactionAuthService;
import br.com.batista.desafio01.service.transactionauth.TransactionAuthService;
import br.com.batista.desafio01.service.transaction.TransactionService;
import br.com.batista.desafio01.service.user.IUserService;
import br.com.batista.desafio01.service.usertype.IUserTypeService;
import br.com.batista.desafio01.utils.MockUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    @Autowired
    private TransactionService transactionService;

    @MockitoBean
    private IUserService userService;

    @Mock
    private IUserTypeService userTypeService;

    @Mock
    private ITransactionRepository transactionRepository;

    @MockitoBean
    private ITransactionAuthService authorizeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Mockito.mockitoSession().initMocks(this);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

    }

    @Test
    public void when_transactions_invalid_value_then_fail() throws Exception {

        TransactionDTO transaction = new TransactionDTO();
        transaction.setValue(new BigDecimal("-1"));
        transaction.setPayee("user1");
        transaction.setPayer("user2");

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.value").value("Não é permitido valores negativos ou zero"))
                .andDo(print());
    }


    @Test
    public void when_transactions_null_value_then_fail() throws Exception {

        TransactionDTO transaction = new TransactionDTO();
        transaction.setValue(null);
        transaction.setPayee("user1");
        transaction.setPayer("user2");

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.value").value("O valor não pode ser nulo"))
                .andDo(print());
    }

    @Test
    public void when_transactions_null_payee_then_fail() throws Exception {

        TransactionDTO transaction = new TransactionDTO();
        transaction.setValue(BigDecimal.TEN);
        transaction.setPayee(null);
        transaction.setPayer("user2");

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.payee").value("O recebedor não pode ser vazio"))
                .andDo(print());
    }


    @Test
    public void when_transactions_null_payer_then_fail() throws Exception {

        TransactionDTO transaction = new TransactionDTO();
        transaction.setValue(BigDecimal.TEN);
        transaction.setPayee("user1");
        transaction.setPayer(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors.payer").value("O pagador não pode ser vazio"))
                .andDo(print());
    }

    @Test
    @Transactional
    public void when_create_user_then_success() throws Exception {

        Transaction transaction = MockUtils.mockTransaction();
        transaction.setValue(BigDecimal.ONE);
        TransactionDTO transactionDTO = new TransactionDTO(transaction);

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        AuthorizeDTO.DataDTO dataDTO = new AuthorizeDTO.DataDTO();
        dataDTO.setAuthorization(true);
        authorizeDTO.setStatus("success");
        authorizeDTO.setData(dataDTO);

        when(userTypeService.findTypeUser()).thenReturn(transaction.getPayer().getUserType());
        when(userService.findByDocumentOrEmail(transactionDTO.getPayer(), transactionDTO.getPayer())).thenReturn(transaction.getPayer());
        when(userService.findByDocumentOrEmail(transactionDTO.getPayee(), transactionDTO.getPayee())).thenReturn(transaction.getPayee());

        when(authorizeService.check("something")).thenReturn(Mono.just(authorizeDTO));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(transactionDTO)))
                .andDo(print());

    }

}
