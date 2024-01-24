package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService = new RecipeServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRecipes() {
        // Mocking repository behavior
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(new Recipe()));

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(1, recipes.size());
    }

    @Test
    void getVegetarianRecipes() {
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(createVegetarianRecipe()));

        List<Recipe> vegetarianRecipes = recipeService.getVegetarianRecipes();

        assertEquals(1, vegetarianRecipes.size());
        assertEquals("Vegetarian Pasta", vegetarianRecipes.get(0).getName());
    }
    @Test
    void updateRecipe() {
        Recipe existingRecipe = new Recipe();  // Existing recipe in the database
        existingRecipe.setId("1");
        when(recipeRepository.findById("1")).thenReturn(Optional.of(existingRecipe));

        Recipe updatedRecipe = new Recipe();  // Updated recipe data
        updatedRecipe.setId("1");
        updatedRecipe.setName("Updated Recipe");

        when(recipeRepository.save(updatedRecipe)).thenReturn(updatedRecipe);

        Recipe result = recipeService.updateRecipe(updatedRecipe);

        assertNotNull(result);
        assertEquals("Updated Recipe", result.getName());
    }

    @Test
    void deleteRecipe() {
        String recipeIdToDelete = "1";
        when(recipeRepository.existsById(recipeIdToDelete)).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(recipeIdToDelete); // Mock the delete operation to do nothing

        boolean result = recipeService.deleteRecipe(recipeIdToDelete);

        assertTrue(result);
        verify(recipeRepository, times(1)).existsById(recipeIdToDelete);
        verify(recipeRepository, times(1)).deleteById(recipeIdToDelete);

    }

    @Test
    void getRecipesByServings() {
        int servings = 4;
        when(recipeRepository.findByServings(servings)).thenReturn(Collections.emptyList());

        List<Recipe> recipes = recipeService.getRecipesByServings(servings);

        assertNotNull(recipes);
        assertTrue(recipes.isEmpty());
    }

    private Recipe createVegetarianRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Vegetarian Pasta");
        recipe.setVegetarian(true);
        recipe.setServings(4);
        recipe.setInstructions("Boil pasta, sauté vegetables, mix with sauce, and serve.");
        recipe.setIngredients(List.of("pasta", "tomatoes", "spinach", "olive oil", "garlic"));
        return recipe;
    }
}
