package br.com.batista.desafio01.service;



import br.com.batista.desafio01.model.dto.AuthorizeDTO;
import br.com.batista.desafio01.model.dto.TransactionDTO;
import br.com.batista.desafio01.model.entities.Transaction;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.repository.ITransactionRepository;

import br.com.batista.desafio01.service.transaction.TransactionService;
import br.com.batista.desafio01.service.transactionauth.ITransactionAuthService;
import br.com.batista.desafio01.service.user.IUserService;
import br.com.batista.desafio01.utils.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    IUserService userService;

    @MockitoBean
    private ITransactionAuthService authorizeService;

    @Mock
    ITransactionRepository transactionRepository;

    @BeforeEach
    public void init(){

        Mockito.mockitoSession().initMocks(this);

    }

    @Test
    public void when_create_transaction_then_success() throws Exception {

        User payer = MockUtils.mockUser();
        User payee = MockUtils.mockUser();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setValue(BigDecimal.TEN);
        transactionDTO.setPayee(payee.getDocument());
        transactionDTO.setPayer(payer.getDocument());

        AuthorizeDTO authorizeDTO = new AuthorizeDTO();
        AuthorizeDTO.DataDTO dataDTO = new AuthorizeDTO.DataDTO();
        dataDTO.setAuthorization(true);
        authorizeDTO.setStatus("success");
        authorizeDTO.setData(dataDTO);

        Transaction transaction = transactionService.toEntity(transactionDTO);

        when(userService.findByDocumentOrEmail(transactionDTO.getPayer(), transactionDTO.getPayer())).thenReturn(payer);
        when(userService.findByDocumentOrEmail(transactionDTO.getPayee(), transactionDTO.getPayee())).thenReturn(payee);
        when(transactionService.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        when(authorizeService.check("something")).thenReturn(Mono.just(authorizeDTO));

        Transaction found = transactionService.processDTO(transactionDTO);

        assertEquals(found.getIdTransaction(), transaction.getIdTransaction());

    }


}
