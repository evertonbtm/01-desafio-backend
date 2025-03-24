package br.com.batista.desafio01.service;


import br.com.batista.desafio01.exception.FieldDuplicatedException;
import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.model.entities.UserType;
import br.com.batista.desafio01.repository.IUserRepository;
import br.com.batista.desafio01.service.user.UserService;
import br.com.batista.desafio01.service.usertype.UserTypeService;
import br.com.batista.desafio01.utils.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserTypeService userTypeService;

    @Mock
    IUserRepository IUserRepository;


    @BeforeEach
    public void init(){

        Mockito.mockitoSession().initMocks(this);

    }

    @Test
    public void when_create_user_then_success() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setName("user1");
        userDTO.setPassword("user123!@#");
        userDTO.setEmail("user1@teste.com");
        userDTO.setDocument("012345678910");

        User user = userService.toEntity(userDTO);

        UserType userType = MockUtils.mockUserType();
        when(userTypeService.findTypeUser()).thenReturn(userType);

        when(IUserRepository.save(Mockito.any(User.class))).thenReturn(user);

        User found = userService.processDTO(userDTO);

        assertEquals(found.getName(), user.getName());

    }

    @Test
    public void when_create_user_then_fail() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setName("user1");
        userDTO.setPassword("user123!@#");
        userDTO.setEmail("user1@teste.com");
        userDTO.setDocument("012345678910");

        UserType userType = MockUtils.mockUserType();
        when(userTypeService.findTypeUser()).thenReturn(userType);

        when(IUserRepository.save(Mockito.any(User.class))).thenReturn(new User());

        User found = userService.processDTO(userDTO);

        assertNotEquals(found.getName(), userDTO.getName());

    }

    @Test
    public void when_create_user_then_return_FieldDuplicatedException() throws Exception {

        List<User> duplicatedList= new ArrayList<>(0);
        UserDTO userDTO = new UserDTO();
        userDTO.setName("user1");
        userDTO.setPassword("user123!@#");
        userDTO.setEmail("user1@teste.com");
        userDTO.setDocument("012345678910");

        User user = userService.toEntity(userDTO);

        duplicatedList.add(user);
        duplicatedList.add(user);

        when(IUserRepository.findListByDocumentOrEmail(userDTO.getDocument(), userDTO.getEmail())).thenReturn(duplicatedList);

        assertThrows(FieldDuplicatedException.class, () -> userService.processDTO(userDTO));

    }


}
