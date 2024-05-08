package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    public Permission() {}
    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
