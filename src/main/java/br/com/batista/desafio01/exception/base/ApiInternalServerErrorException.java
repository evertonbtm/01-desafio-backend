package br.com.batista.desafio01.exception.base;

public class ApiInternalServerErrorException extends RuntimeException{

    public ApiInternalServerErrorException(String message){
        super(message);
    }

    public ApiInternalServerErrorException(){
        super();
    }

}
