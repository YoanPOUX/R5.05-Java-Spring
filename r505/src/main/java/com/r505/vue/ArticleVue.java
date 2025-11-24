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

}
