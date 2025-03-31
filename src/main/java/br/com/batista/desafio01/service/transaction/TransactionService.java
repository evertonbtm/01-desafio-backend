package br.com.batista.desafio01.service.transaction;

import br.com.batista.desafio01.exception.*;
import br.com.batista.desafio01.model.dto.AuthorizeDTO;
import br.com.batista.desafio01.model.dto.TransactionDTO;
import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.model.entities.Transaction;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.enums.EUserType;
import br.com.batista.desafio01.repository.ITransactionRepository;
import br.com.batista.desafio01.service.notification.INotificationService;
import br.com.batista.desafio01.service.transactionauth.ITransactionAuthService;
import br.com.batista.desafio01.service.user.IUserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    IUserService userService;

    @Autowired
    ITransactionAuthService authorizeService;

    @Autowired
    INotificationService notificationService;

    @Override
    public TransactionDTO toDTO(Transaction entity){
        return entity == null ? null : new TransactionDTO(entity);
    }

    @Override
    public List<TransactionDTO> toDTO(List<Transaction> entityList){
        return entityList.stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @Override
    public Transaction toEntity(TransactionDTO dto){
        return toEntity(new Transaction(), dto);
    }

    @Override
    public Transaction toEntity(Transaction transaction, TransactionDTO dto){

        transaction.setValue(dto.getValue());
        transaction.setCreateDate(dto.getCreateDate());
        transaction.setMovimentDate(dto.getMovimentDate());

        return transaction;
    }

    @Transactional
    @Override
    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findByUser(String userDocumentOrEmail) throws Exception {
        List<Transaction> transactionList = transactionRepository.findListByUser(userDocumentOrEmail);

        if(transactionList == null || transactionList.isEmpty()){
            return null;
        }

        if(transactionList.size() > 1){
          //  throw new FieldDuplicatedException(Transaction.class, "document", userDocumentOrEmail);
        }

        return transactionList.get(0);
    }

    @Transactional
    @Override
    public Transaction processDTO(TransactionDTO transactionDTO) throws Exception {

        Transaction transaction = new Transaction();

        transaction.setValue(transactionDTO.getValue());
        transaction.setCreateDate(transactionDTO.getCreateDate());
        transaction.setMovimentDate(transactionDTO.getMovimentDate());

        validatePayerUser(transaction, transactionDTO);
        validatePayeeUser(transaction, transactionDTO);

        validatePayerBalance(transaction);

        validateAuthorization(transaction, transactionDTO);

        Transaction response = save(transaction);

        calculatePayerBalance(transaction);
        calculatePayeeBalance(transaction);

        notificationService.notify(response);

        return response;
    }

    private void validatePayerUser(Transaction transaction, TransactionDTO transactionDTO) throws Exception {
        User payer = userService.findByDocumentOrEmail(transactionDTO.getPayer(), transactionDTO.getPayer());

        if(payer == null){
            throw new UserNotFoundException(User.class, "document or email", transactionDTO.getPayer());
        }

        if(payer.getUserType().getType().equals(EUserType.SHOPKEEPER.get())){
            throw new UserTypeTransactionException(User.class,"userType","");
        }

        transaction.setPayer(payer);
    }

    private void validatePayeeUser(Transaction transaction, TransactionDTO transactionDTO) throws Exception {
        User payer = userService.findByDocumentOrEmail(transactionDTO.getPayee(), transactionDTO.getPayee());

        if(payer == null){
            throw new UserNotFoundException(User.class, "document or email", transactionDTO.getPayer());
        }

        transaction.setPayee(payer);
    }

    private void  validatePayerBalance(Transaction transaction){

        User payer = transaction.getPayer();
        if(payer.getMoneyBalance().compareTo(transaction.getValue()) < 0){
            throw new InsuficientBalanceException(User.class,"moneyBalance", payer.getMoneyBalance().toPlainString());
        }

    }

    private void calculatePayerBalance(Transaction transaction) throws Exception {
        User payer = transaction.getPayer();

        BigDecimal userBalance = payer.getMoneyBalance();

        BigDecimal newBalance = userBalance.subtract(transaction.getValue());
        payer.setMoneyBalance(newBalance);

        UserDTO userDTO = userService.toDTO(payer);
        userService.processDTO(userDTO);
    }

    private void calculatePayeeBalance(Transaction transaction) throws Exception {
        User payee = transaction.getPayee();

        BigDecimal userBalance = payee.getMoneyBalance();

        BigDecimal newBalance = userBalance.add(transaction.getValue());
        payee.setMoneyBalance(newBalance);

        UserDTO userDTO = userService.toDTO(payee);
        userService.processDTO(userDTO);
    }

    private void validateAuthorization(Transaction transaction, TransactionDTO transactionDTO) throws Exception {
        AuthorizeDTO authorizeDTO;
        try {
             authorizeDTO = authorizeService.check("something").block();
            if(authorizeDTO == null){
                throw new UnavailableException(Transaction.class);
            }
        } catch (Exception e){
            throw new UnavailableException(Transaction.class);
        }

        if (authorizeDTO.getStatus().equals("success") && authorizeDTO.getData().isAuthorization()) {
            logger.info("Transaction auth "+authorizeDTO.getStatus());
        }else{
            throw new UnauthorizedException(Transaction.class);
        }

    }
}
