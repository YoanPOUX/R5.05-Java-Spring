package com.r505.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r505.modele.Article;
import com.r505.modele.ArticleRepository;
import com.r505.modele.User;
import com.r505.modele.UserRepository;
import com.r505.modele.Reaction;
import com.r505.modele.ReactionRepository;
import com.r505.modele.ReactionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReactionRepository reactionRepository;

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

    public boolean likeArticle(Integer articleId, Integer userId) {
        Optional<Article> aOpt = articleRepository.findById(articleId);
        Optional<User> uOpt = userRepository.findById(userId);
        if (aOpt.isEmpty() || uOpt.isEmpty()) {
            return false;
        }
        Article article = aOpt.get();
        User user = uOpt.get();
        Optional<Reaction> rOpt = reactionRepository.findByUser_IdAndArticle_Id(userId, articleId);
        if (rOpt.isPresent()) {
            Reaction r = rOpt.get();
            if (r.getType() == ReactionType.LIKE) {
                return true; // already liked
            }
            r.setType(ReactionType.LIKE);
            reactionRepository.save(r);
            return true;
        }
        Reaction r = new Reaction(user, article, ReactionType.LIKE);
        reactionRepository.save(r);
        return true;
    }

    public boolean dislikeArticle(Integer articleId, Integer userId) {
        Optional<Article> aOpt = articleRepository.findById(articleId);
        Optional<User> uOpt = userRepository.findById(userId);
        if (aOpt.isEmpty() || uOpt.isEmpty()) {
            return false;
        }
        Article article = aOpt.get();
        User user = uOpt.get();
        Optional<Reaction> rOpt = reactionRepository.findByUser_IdAndArticle_Id(userId, articleId);
        if (rOpt.isPresent()) {
            Reaction r = rOpt.get();
            if (r.getType() == ReactionType.DISLIKE) {
                return true; // already disliked
            }
            r.setType(ReactionType.DISLIKE);
            reactionRepository.save(r);
            return true;
        }
        Reaction r = new Reaction(user, article, ReactionType.DISLIKE);
        reactionRepository.save(r);
        return true;
    }

    public boolean removeReaction(Integer articleId, Integer userId) {
        Optional<Reaction> rOpt = reactionRepository.findByUser_IdAndArticle_Id(userId, articleId);
        if (rOpt.isPresent()) {
            reactionRepository.delete(rOpt.get());
            return true;
        }
        return false;
    }

    public List<User> getLikers(Integer articleId) {
        return reactionRepository.findAllByArticle_IdAndType(articleId, ReactionType.LIKE)
                .stream().map(Reaction::getUser).collect(Collectors.toList());
    }

    public List<User> getDislikers(Integer articleId) {
        return reactionRepository.findAllByArticle_IdAndType(articleId, ReactionType.DISLIKE)
                .stream().map(Reaction::getUser).collect(Collectors.toList());
    }
}
