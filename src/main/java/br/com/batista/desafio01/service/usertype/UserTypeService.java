package br.com.batista.desafio01.service.usertype;

import br.com.batista.desafio01.exception.FieldDuplicatedException;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.model.enums.EUserType;
import br.com.batista.desafio01.repository.IUserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService implements IUserTypeService {

    @Autowired
    IUserTypeRepository userTypeRepository;


    @Override
    public UserType save(UserType UserType){
        return userTypeRepository.save(UserType);
    }

    @Override
    public UserType findByType(String type) throws Exception {
        List<UserType> UserTypeList = userTypeRepository.findListByType(type);

        if(UserTypeList == null || UserTypeList.isEmpty()){
            return null;
        }

        if(UserTypeList.size() > 1){
            throw new FieldDuplicatedException(UserType.class, "type", type);
        }

        return UserTypeList.get(0);
    }

    public UserType findTypeShopkeeper() throws Exception {
        return findByType(EUserType.SHOPKEEPER.get());
    }

    public UserType findTypeUser() throws Exception {
        return findByType(EUserType.USER.get());
    }


}
