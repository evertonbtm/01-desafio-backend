package br.com.batista.desafio01.model.enums;

public enum EUserType {
    USER("USER"),
    SHOPKEEPER("SHOPKEEPER");

    private final String userType;

    public String get(){
        return this.userType;
    }

    EUserType(String userType){
        this.userType = userType;
    }
}
