package com.br.controller;


import com.br.exception.ResourceNotFoundException;
import com.br.model.User;
import com.br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //inserindo um novo user
    //POST - http://localhost:8080/cuser/user - dados do usuario a ser inserido

    @PostMapping("/user")
    public User save(@RequestBody User user) {
        return this.uRep.save(user);
    }

    @PutMapping("/user/{id}/tarefa")
    public String atribuirTarefa(@PathVariable Long id, @RequestParam("tarefa") String tarefa) {
        return "tarefa";
    }

    //alterando um usuario existente no banco de dados
    //PUT - http://localhost:8080/cuser/user/{id} + dados alterados no sistema
    @PutMapping("/user/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {

        User u = this.uRep.findById(id).orElseThrow(()->
        new ResourceNotFoundException("usuario nao encontrado" +id));
        //altera o id do projeto de acordo com o que é colocado na url
        u.setId(id);
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());

        User userAtualizado = this.uRep.save(u);
        return ResponseEntity.ok(userAtualizado);
    }

    //Excluir um usuario existente no banco de dados ou do projeto que esta sendo executado
    //DELETE -  http://localhost:8080/cuser/user/{id}
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id){
        User user = this.uRep.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("usuario nao encontrado" +id));

        this.uRep.delete(user);
        Map<String, Boolean> resposta = new HashMap<>();
        resposta.put("excluido",true);
        return ResponseEntity.ok(resposta);
    }

}
