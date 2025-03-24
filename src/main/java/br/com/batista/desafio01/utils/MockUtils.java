package br.com.batista.desafio01.utils;

import br.com.batista.desafio01.model.entities.Transaction;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.model.enums.EUserType;

import java.math.BigDecimal;
import java.util.Date;

public class MockUtils {

    public static UserType mockUserType(){
        UserType userType = new UserType();
        userType.setIdUserType(RandomicUtils.generateNumeric(2));
        userType.setIdUserType(RandomicUtils.generateNumeric(6));
        userType.setType(EUserType.USER.get());
        userType.setActive(true);

        return userType;
    }

    public static User mockUser(){
        User user = new User();
        user.setIdUser(RandomicUtils.generateNumeric(2));
        user.setName(RandomicUtils.generateAlphaNumeric(6));
        user.setPassword(RandomicUtils.generateAlphaNumeric(6));
        user.setEmail(RandomicUtils.generateRandomEmail());
        user.setMoneyBalance(new BigDecimal(RandomicUtils.generateNumeric(4)));
        user.setDocument(RandomicUtils.generateNumeric(9).toString());
        user.setUserType(mockUserType());

        return user;
    }

    public static Transaction mockTransaction(){
        Transaction transaction = new Transaction();
        transaction.setIdTransaction(RandomicUtils.generateNumeric(2));
        transaction.setValue(new BigDecimal(RandomicUtils.generateNumeric(4)));
        transaction.setPayee(mockUser());
        transaction.setPayer(mockUser());
        transaction.setMovimentDate(new Date());
        transaction.setCreateDate(new Date());

        return  transaction;
    }
}
