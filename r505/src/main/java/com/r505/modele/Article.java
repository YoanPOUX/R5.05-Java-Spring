package com.r505.modele;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date datePublication;
    // private User auteur; --> foreign id vers user
    
}
