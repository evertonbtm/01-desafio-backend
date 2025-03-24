package br.com.batista.desafio01.controller;

import br.com.batista.desafio01.model.dto.TransactionDTO;
import br.com.batista.desafio01.service.transaction.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    @Autowired
    ITransactionService transactionService;


    public TransactionController(){

    }

    @PostMapping(path="send")
    public ResponseEntity<TransactionDTO> send(@Valid @RequestBody TransactionDTO transactionDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new TransactionDTO(transactionService.processDTO(transactionDTO)));
    }

}
