package com.r505.vue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.r505.controller.ArticleService;
import com.r505.modele.Article;
import com.r505.modele.User;
import java.util.List;


@Controller
@RequestMapping(path="/article")
public class ArticleVue {
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("all")
    public @ResponseBody Iterable<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
    
    @GetMapping(path="/{id}")
    public @ResponseBody Article getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewArticle (@RequestBody Article article) {
        Integer auteurId = null;
        if (article.getAuteur() != null) {
            auteurId = article.getAuteur().getId();
        }
        articleService.addArticle(article.getDatePublication(), auteurId, article.getTitre(), article.getContenu());
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody Article updateArticle(@RequestBody Article article) {
        Integer auteurId = null;
        if (article.getAuteur() != null) {
            auteurId = article.getAuteur().getId();
        }
        return articleService.updateArticle(article.getId(), article.getDatePublication(), auteurId, article.getTitre(), article.getContenu());
    }

    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody String deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return "Deleted";
    }

    @PostMapping(path="/{id}/like/{userId}")
    public @ResponseBody String likeArticle(@PathVariable Integer id, @PathVariable Integer userId) {
        boolean ok = articleService.likeArticle(id, userId);
        return ok ? "Liked" : "Error";
    }

    @PostMapping(path="/{id}/dislike/{userId}")
    public @ResponseBody String dislikeArticle(@PathVariable Integer id, @PathVariable Integer userId) {
        boolean ok = articleService.dislikeArticle(id, userId);
        return ok ? "Disliked" : "Error";
    }

    @DeleteMapping(path="/{id}/reaction/{userId}")
    public @ResponseBody String removeReaction(@PathVariable Integer id, @PathVariable Integer userId) {
        boolean ok = articleService.removeReaction(id, userId);
        return ok ? "Removed" : "NotFound";
    }

    @GetMapping(path="/{id}/likes")
    public @ResponseBody List<User> getLikers(@PathVariable Integer id) {
        return articleService.getLikers(id);
    }

    @GetMapping(path="/{id}/dislikes")
    public @ResponseBody List<User> getDislikers(@PathVariable Integer id) {
        return articleService.getDislikers(id);
    }

}
