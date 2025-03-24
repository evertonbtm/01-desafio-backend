package br.com.batista.desafio01.service.user;

import br.com.batista.desafio01.exception.FieldDuplicatedException;
import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.model.entities.User;
import br.com.batista.desafio01.repository.IUserRepository;
import br.com.batista.desafio01.service.usertype.IUserTypeService;
import br.com.batista.desafio01.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserTypeService userTypeService;

    @Override
    public UserDTO toDTO(User entity){
        return entity == null ? null : new UserDTO(entity);
    }

    @Override
    public List<UserDTO> toDTO(List<User> entityList){
        return entityList.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public User toEntity(UserDTO dto){
        return toEntity(new User(), dto);
    }

    @Override
    public User toEntity(User user, UserDTO dto){

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setDocument(dto.getDocument());
        user.setPassword(dto.getPassword());

        return user;
    }

    private void remove(User user){
        userRepository.delete(user);
    }


    @Override
    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    public User findByDocumentOrEmail(String document, String email) throws Exception {
        List<User> userList = userRepository.findListByDocumentOrEmail(document, email);

        if(userList == null || userList.isEmpty()){
            return null;
        }

        if(userList.size() > 1){
            throw new FieldDuplicatedException(User.class, "document", document);
        }

        return userList.get(0);
    }

    @Override
    public User processDTO(UserDTO userDTO) throws Exception {

        User user = findByDocumentOrEmail(userDTO.getDocument(), userDTO.getEmail());

        if(user == null){
            user = new User();
            user.setDocument(userDTO.getDocument());
            user.setEmail(userDTO.getEmail());
        }

        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());

        validateUserType(user);

        return save(user);
    }

    private void validateUserType(User user) throws Exception {
        user.setUserType(userTypeService.findTypeUser());

        if(ValidationUtils.isValidCpfOrCnpj(user.getDocument())
                && ValidationUtils.isValidCnpj(user.getDocument())){

            user.setUserType(userTypeService.findTypeShopkeeper());

        }
    }

}
