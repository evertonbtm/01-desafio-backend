package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class InsuficientBalanceException extends ApiInternalServerErrorException {

    private String code;

    public InsuficientBalanceException(Class clazz, String field, String balance){
        super(clazz.getSimpleName() + " : " + field + " nao existe saldo suficiente "+balance);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
