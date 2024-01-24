package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public boolean deleteRecipe(String recipeId) {
        if (recipeRepository.existsById(recipeId)) {
            recipeRepository.deleteById(recipeId);
            return true;
        }
        return false;
    }
    @Override
    public List<Recipe> searchRecipes(SearchCriteria criteria) {
        // Fetch all recipes from the repository
        List<Recipe> allRecipes = recipeRepository.findAll();
        // Apply filtering based on the search criteria
        return allRecipes.stream()
                .filter(recipe ->
                        (criteria.isVegetarian() == recipe.isVegetarian()) &&
                                (criteria.getServings() <= 0 || recipe.getServings() == criteria.getServings()) &&
                                (criteria.getIngredient() == null || recipe.getIngredients().contains(criteria.getIngredient())) &&
                                (criteria.getExcludedIngredient() == null || !recipe.getIngredients().contains(criteria.getExcludedIngredient())) &&
                                (criteria.getInstructionKeyword() == null || recipe.getInstructions().contains(criteria.getInstructionKeyword()))
                )
                .collect(Collectors.toList());
    }
}
