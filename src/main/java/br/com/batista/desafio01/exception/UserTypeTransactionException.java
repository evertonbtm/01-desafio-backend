package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class UserTypeTransactionException extends ApiInternalServerErrorException {

    private String code;

    public UserTypeTransactionException(Class clazz, String field, String type){
        super(clazz.getSimpleName() + " : " + field + " Não é permitido realizar trasnferencia como Lojista");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
