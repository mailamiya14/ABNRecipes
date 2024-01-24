package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(String id, Recipe recipe) {
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
