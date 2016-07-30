package com.example.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Account {

    public Account(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String username, password;
    private boolean active;

    public String getUsername() {
        return username;
    }

    Account() {// why JPA why??
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }
}