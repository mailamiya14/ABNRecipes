package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(String id);

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe recipe);

    boolean deleteRecipe(String id);
    List<Recipe> getVegetarianRecipes();

    List<Recipe> getRecipesByServings(int servings);

    List<Recipe> getRecipesByIngredients(List<String> includeIngredients, List<String> excludeIngredients);

    List<Recipe> getRecipesByInstructions(String searchKeyword);
}
