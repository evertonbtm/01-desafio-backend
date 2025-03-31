package br.com.batista.desafio01.model.enums;

public enum ENotification {

    TITLE("Payment Notification"),
    MESSAGE("You have received a payment of R$ {0} from {1}");

    private final String userType;

    public String get(){
        return this.userType;
    }

    ENotification(String userType){
        this.userType = userType;
    }
}
