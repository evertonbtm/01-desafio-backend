package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class UserNotFoundException extends ApiInternalServerErrorException {

    private String code;

    public UserNotFoundException(Class clazz, String field, String code){
        super(clazz.getSimpleName() + " :  User not found "+ field + " = "+code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
