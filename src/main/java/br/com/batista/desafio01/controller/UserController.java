package br.com.batista.desafio01.controller;

import br.com.batista.desafio01.model.dto.UserDTO;
import br.com.batista.desafio01.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;


    public UserController(){

    }

    @PostMapping(path="create")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.processDTO(userDTO)));
    }

}
