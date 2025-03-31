package br.com.batista.desafio01.model.entities;

import br.com.batista.desafio01.model.entities.base.BaseUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;

@Entity
@Table(name = "Users", uniqueConstraints = { @UniqueConstraint(name = "UC_USERDOC", columnNames = {"document"}), @UniqueConstraint(name = "UC_USERMAIL", columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pkuser")
    @SequenceGenerator( name = "seq_pkuser", sequenceName = "seqpkuser", allocationSize = 1)
    @Column(name = "idUser")
    private long idUser;

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

    @Column(name="phone")
    @Size(max= 20)
    private String phone;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="userType", nullable = false)
    @NotNull
    private UserType userType;

    @Column(name="moneyBalance")
    @NotNull
    BigDecimal moneyBalance = BigDecimal.ZERO;

    @Column(name="isSendMoney", nullable = true)
    boolean isSendMoney = true;

    @Column(name="isReceiveMoney", nullable = true)
    boolean isReceiveMoney= true;


    public User(){

    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}