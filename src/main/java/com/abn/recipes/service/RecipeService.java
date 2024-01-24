package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(String id);

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe recipe);

    boolean deleteRecipe(String id);

    List<Recipe> searchRecipes(SearchCriteria criteria);

}
