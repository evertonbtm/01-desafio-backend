package br.com.batista.desafio01.model.entities;

import br.com.batista.desafio01.model.entities.base.BaseUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "UserType", uniqueConstraints = { @UniqueConstraint(name = "UC_USERTYPE", columnNames = {"type"})})
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pkusertype")
    @SequenceGenerator( name = "seq_pkusertype", sequenceName = "seqpkusertype", allocationSize = 1)
    @Column(name = "idUserType")
    private long idUserType;

    @Column(name = "type")
    @Size(max= 10)
    @NotBlank
    private String type;

    @Column(name = "isActive")
    private boolean isActive;


    public UserType(){
        
    }

    public long getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(long idUserType) {
        this.idUserType = idUserType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}