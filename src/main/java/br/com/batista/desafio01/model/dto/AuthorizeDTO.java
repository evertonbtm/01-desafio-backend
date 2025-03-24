package br.com.batista.desafio01.model.dto;

public class AuthorizeDTO {

    public static class  DataDTO {
        boolean authorization;

        public DataDTO(){

        }

        public boolean isAuthorization() {
            return authorization;
        }

        public void setAuthorization(boolean authorization) {
            this.authorization = authorization;
        }
    }

    String status;

    DataDTO data;

    public AuthorizeDTO(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
}
