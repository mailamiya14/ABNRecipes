package com.abn.recipes.repository;

import com.abn.recipes.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByServings(int servings);
}
