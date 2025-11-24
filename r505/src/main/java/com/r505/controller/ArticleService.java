package com.r505.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r505.modele.Article;
import com.r505.modele.ArticleRepository;
import com.r505.modele.User;
import com.r505.modele.UserRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    public Article addArticle(LocalDateTime datePublication, Integer auteurId, String titre, String contenu) {
        User auteur = null;
        if (auteurId != null) {
            auteur = userRepository.findById(auteurId).orElse(null);
        }
        Article article = new Article();
        article.setDatePublication(datePublication);
        article.setAuteur(auteur);
        article.setTitre(titre);
        article.setContenu(contenu);
        return articleRepository.save(article);
    }
    
    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    public Article updateArticle(Integer id, LocalDateTime datePublication, Integer auteur_id, String titre, String contenu) {
        return articleRepository.findById(id).map(article -> {
            if (datePublication != null) {
                article.setDatePublication(datePublication);
            }
            if (auteur_id != null) {
                User u = userRepository.findById(auteur_id).orElse(null);
                article.setAuteur(u);
            }
            if (titre != null) {
                article.setTitre(titre);
            }
            if (contenu != null) {
                article.setContenu(contenu);
            }
            return articleRepository.save(article);
        }).orElse(null);
    }
}
