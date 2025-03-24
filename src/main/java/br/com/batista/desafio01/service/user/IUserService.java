package br.com.batista.desafio01.service.user;

import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.model.entities.User;

import java.util.List;

public interface IUserService {

    User save(User user);

    UserDTO toDTO(User entity);

    List<UserDTO> toDTO(List<User> entityList);

    User toEntity(UserDTO dto);

    User toEntity(User user, UserDTO dto);

    User findByDocumentOrEmail(String document, String email) throws Exception;

    User processDTO(UserDTO userDTO) throws Exception;
}
