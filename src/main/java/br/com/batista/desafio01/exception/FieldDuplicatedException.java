package br.com.batista.desafio01.exception;

import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;

public class FieldDuplicatedException extends ApiInternalServerErrorException {

    private String code;

    public FieldDuplicatedException (Class clazz, String field, String code){
        super(clazz.getSimpleName() + " : " + field + " is duplicated. code = "+code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
