package com.npichuzhkin.JsonViewTask.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private List<Order> orders;

    public User(){}
    public User (String name, String email){
        this.name = name;
        this.email = email;
    }

    public User (UUID id, String name, String email){
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public User (UUID id, String name, String email, List<Order> orders){
        this.name = name;
        this.email = email;
        this.id = id;
        this.orders = orders;
    }
}
