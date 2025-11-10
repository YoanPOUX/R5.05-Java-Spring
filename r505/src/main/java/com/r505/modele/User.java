package com.r505.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private Boolean isModerator;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String psw) {
        this.password = psw;
    }

    public Boolean getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(Boolean isModerator) {
        this.isModerator = isModerator;
    }
}