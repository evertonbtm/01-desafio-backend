package br.com.batista.desafio01.service.usertype;

import br.com.batista.desafio01.model.entities.UserType;

public interface IUserTypeService {

    UserType save(UserType UserType);

    UserType findByType(String type) throws Exception;

    UserType findTypeShopkeeper() throws Exception;

    UserType findTypeUser() throws Exception;

}
