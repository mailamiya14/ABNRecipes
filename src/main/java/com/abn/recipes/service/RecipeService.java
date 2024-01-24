package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(String id);

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(String id, Recipe recipe);

    void deleteRecipe(String id);
}
