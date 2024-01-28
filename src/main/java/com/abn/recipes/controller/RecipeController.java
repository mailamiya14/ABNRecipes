package com.abn.recipes.controller;

import com.abn.recipes.exceptions.InvalidSearchCriteriaException;
import com.abn.recipes.exceptions.RecipeNotFoundException;
import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    /**
     * Get all recipes.
     *
     * @return List of recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /**
     * Get all recipes.
     *
     * @return a recipes
     */
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe == null) {
            throw new RecipeNotFoundException("Recipe not found with id: " + id);
        }

        return new ResponseEntity<>(recipe, HttpStatus.OK).getBody();
    }

    /**
     * Get all recipes.
     *
     * @return List of recipes
     */
    @PostMapping("/add-recipe")
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    /**
     * Get all recipes.
     *
     * @return List of recipes
     */
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(recipe);

        if (updatedRecipe == null) {
            throw new RecipeNotFoundException("Recipe not found with id: " + id);
        }

        return updatedRecipe;
    }

    /**
     * Get all recipes.
     *
     * @return List of recipes
     */
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }

    @PostMapping("/search-recipes")
    public List<Recipe> searchRecipes(@RequestBody SearchCriteria criteria) {
        try {
            validateSearchCriteria(criteria);
            return recipeService.searchRecipes(criteria);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidSearchCriteriaException("Invalid search criteria: " + e.getMessage());
        }

    }
    private void validateSearchCriteria(SearchCriteria criteria) {
        if (criteria == null) {
            throw new InvalidSearchCriteriaException("Search criteria cannot be null.");
        }
        if (criteria.getServings() != 0) {
            validateServings(String.valueOf(criteria.getServings()));
        }
    }

    private void validateServings(String servings) {
        try {
            int servingsValue = Integer.parseInt(servings);
            if (servingsValue <= 0) {
                throw new InvalidSearchCriteriaException("Servings must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidSearchCriteriaException("Invalid servings value: " + servings + ". Servings must be a valid integer.");
        }
    }
}
