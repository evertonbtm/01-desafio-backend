package br.com.batista.desafio01.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transaction", uniqueConstraints = {})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pktransaction")
    @SequenceGenerator( name = "seq_pktransaction", sequenceName = "seqpktransaction", allocationSize = 1)
    @Column(name = "idTransaction")
    private long idTransaction;

    @Column(name = "value")
    @NotNull
    BigDecimal value;

    @ManyToOne
    @JoinColumn(name="userPayer", nullable = false)
    @NotNull
    User payer;

    @ManyToOne
    @JoinColumn(name="userPayee", nullable = false)
    @NotNull
    User payee;

    @Column(name = "createDate")
    @NotNull
    Date createDate;

    @Column(name = "movimentDate")
    @NotNull
    Date movimentDate;

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

    public User getPayee() {
        return payee;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(long idTransaction) {
        this.idTransaction = idTransaction;
    }
}
