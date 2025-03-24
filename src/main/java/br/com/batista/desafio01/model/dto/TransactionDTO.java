package br.com.batista.desafio01.model.dto;


import br.com.batista.desafio01.model.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {

    @JsonIgnore
    private long idTransaction;

    @NotNull(message = "O valor não pode ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "Não é permitido valores negativos ou zero")
    BigDecimal value;

    @NotBlank(message = "O pagador não pode ser vazio")
    String payer;

    @NotBlank(message = "O recebedor não pode ser vazio")
    String payee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date movimentDate;

    public TransactionDTO(){

    }

    public TransactionDTO(Transaction transaction){

        this.setIdTransaction(transaction.getIdTransaction());
        this.setValue(transaction.getValue());
        //user
        if(transaction.getPayer() != null){
            this.setPayer(transaction.getPayer().getDocument());
        }
        if(transaction.getPayee() != null){
            this.setPayee(transaction.getPayee().getDocument());
        }
        this.setCreateDate(transaction.getCreateDate());
        this.setMovimentDate(transaction.getMovimentDate());

    }

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Date getMovimentDate() {
        return movimentDate;
    }

    public void setMovimentDate(Date movimentDate) {
        this.movimentDate = movimentDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
