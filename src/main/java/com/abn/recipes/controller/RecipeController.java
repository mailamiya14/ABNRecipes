package com.abn.recipes.controller;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return recipeService.getRecipeById(id);
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
        return recipeService.updateRecipe(recipe);
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
        return recipeService.searchRecipes(criteria);
    }
}
