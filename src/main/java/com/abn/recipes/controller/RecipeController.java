package com.abn.recipes.controller;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }
    @GetMapping("/vegetarian")
    public List<Recipe> getVegetarianRecipes() {
        return recipeService.getVegetarianRecipes();
    }

    @GetMapping("/servings/{servings}")
    public List<Recipe> getRecipesByServings(@PathVariable int servings) {
        return recipeService.getRecipesByServings(servings);
    }

    @GetMapping("/ingredients")
    public List<Recipe> getRecipesByIngredients(
            @RequestParam(required = false) List<String> include,
            @RequestParam(required = false) List<String> exclude) {
        return recipeService.getRecipesByIngredients(include, exclude);
    }

    @GetMapping("/instructions")
    public List<Recipe> getRecipesByInstructions(@RequestParam String searchKeyword) {
        return recipeService.getRecipesByInstructions(searchKeyword);
    }
}
