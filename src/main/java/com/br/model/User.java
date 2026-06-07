package com.br.model;

import jakarta.persistence.*;

@Entity
@Table(name="tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "tarefa")
    private String tarefa;

    //construtor padrao
    public User() {
        super();
    }
    //construtor com todos os atributos
    public User(Long id, String name, String email, String password, String tarefa) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tarefa = tarefa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTarefa() {return tarefa;};

    public void setTarefa(String tarefa) {this.tarefa = tarefa;}
}
