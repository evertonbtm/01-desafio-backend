package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class UnauthorizedException extends ApiInternalServerErrorException {

    private String code;

    public UnauthorizedException(Class clazz){
        super(clazz.getSimpleName() + " : Não é permitido realizar transferencia");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
