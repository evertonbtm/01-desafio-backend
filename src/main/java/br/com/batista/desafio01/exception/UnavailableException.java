package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class UnavailableException extends ApiInternalServerErrorException {

    private String code;

    public UnavailableException(Class clazz){
        super(clazz.getSimpleName() + " : falha ao consultar serviços, realize uma nova tentativa em breve");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
