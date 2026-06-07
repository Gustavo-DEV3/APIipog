package com.br.controller;


import com.br.exception.ResourceNotFoundException;
import com.br.model.User;
import com.br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// o endereço dos serviços será: http://localhost:8080/cuser/<>

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cuser/")
@RestController
public class UserController {

    // cria uma instancia do repositorio JPA Hibernate
    @Autowired
    private UserRepository uRep;

    //lista todos os usuarios
    //GET - http://localhost:8080/cuser/user
    @GetMapping("/user")
    public List<User> listar() {
        return this.uRep.findAll();
    }

    //consultar um usuario em especifico
    //GET - http://localhost:8080/cuser/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<User> consultar(@PathVariable Long id) {

        User user = this.uRep.findById(id).orElseThrow(()->
        new ResourceNotFoundException("usuario no encontrado" +id));
        return ResponseEntity.ok(user);
    }

}
