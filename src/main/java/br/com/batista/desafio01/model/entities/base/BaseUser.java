package br.com.batista.desafio01.model.entities.base;

import br.com.batista.desafio01.model.entities.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@MappedSuperclass
public abstract class BaseUser {

    @Column(name = "name")
    @Size(max= 50)
    @NotBlank
    private String name;

    @Column(name = "password")
    @Size(max= 30)
    @NotBlank
    private String password;

    @Column(name = "document")
    @Size(max= 15)
    @NotBlank
    private String document;

    @Column(name = "email")
    @Size(max= 60)
    @NotBlank
    private String email;

    @ManyToOne
    @JoinColumn(name="id")
    private UserType userType;

    @Column(name="moneyBalance")
    @NotNull
    BigDecimal moneyBalance = BigDecimal.ZERO;

    @Column(name="isSendMoney", nullable = true)
    boolean isSendMoney = true;

    @Column(name="isReceiveMoney", nullable = true)
    boolean isReceiveMoney= true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReceiveMoney() {
        return isReceiveMoney;
    }

    public void setReceiveMoney(boolean receiveMoney) {
        isReceiveMoney = receiveMoney;
    }

    public boolean isSendMoney() {
        return isSendMoney;
    }

    public void setSendMoney(boolean sendMoney) {
        isSendMoney = sendMoney;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(BigDecimal moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
