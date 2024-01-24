package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Recipe> getVegetarianRecipes() {
        return recipeRepository.findAll().stream()
                .filter(Recipe::isVegetarian)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getRecipesByServings(int servings) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getServings() == servings)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getRecipesByIngredients(List<String> includeIngredients, List<String> excludeIngredients) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> includeIngredients.isEmpty() || recipe.getIngredients().containsAll(includeIngredients))
                .filter(recipe -> excludeIngredients.isEmpty() || excludeIngredients.stream().noneMatch(recipe.getIngredients()::contains))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> getRecipesByInstructions(String searchKeyword) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getInstructions().contains(searchKeyword))
                .collect(Collectors.toList());
    }
}
