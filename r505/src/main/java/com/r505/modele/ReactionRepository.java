package com.r505.modele;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ReactionRepository extends CrudRepository<Reaction, Integer> {
    Optional<Reaction> findByUser_IdAndArticle_Id(Integer userId, Integer articleId);
    List<Reaction> findAllByArticle_IdAndType(Integer articleId, ReactionType type);
}
