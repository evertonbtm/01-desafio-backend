package br.com.batista.desafio01.service.transaction;

import br.com.batista.desafio01.model.dto.TransactionDTO;
import br.com.batista.desafio01.model.entities.Transaction;

import java.util.List;

public interface ITransactionService {

    TransactionDTO toDTO(Transaction entity);

    List<TransactionDTO> toDTO(List<Transaction> entityList);

    Transaction toEntity(TransactionDTO dto);

    Transaction toEntity(Transaction transaction, TransactionDTO dto);

    Transaction save(Transaction transaction);

    Transaction findByUser(String userDocumentOrEmail) throws Exception;

    Transaction processDTO(TransactionDTO transactionDTO) throws Exception;
}
