package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testSearchRecipes_Vegetarian() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1));

        List<Recipe> result = recipeService.searchRecipes(new SearchCriteria(true, 0, null, null, null));

        assertThat(result).containsExactly(recipe1);
    }
    @Test
    void testSearchRecipes_ServingsAndIngredient() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        Recipe recipe2 = new Recipe("2", "Chicken Stir Fry", false, 3, Arrays.asList("chicken", "vegetables", "soy sauce"), "Stir-fry chicken and vegetables. Add soy sauce. Serve hot.");
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> result = recipeService.searchRecipes(new SearchCriteria(false, 4, "rice", null, null));

        assertThat(result).isEmpty();
    }

    @Test
    void testSearchRecipes_ExcludedIngredientAndInstructionKeyword() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        Recipe recipe2 = new Recipe("2", "Chicken Stir Fry", false, 3, Arrays.asList("chicken", "vegetables", "soy sauce"), "Stir-fry chicken and vegetables. Add soy sauce. Serve hot.");
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> result = recipeService.searchRecipes(new SearchCriteria(false, 0, "pasta", "stir-fry", "chicken"));

        assertThat(result).isEmpty(); // Expects an empty list because both recipes do not match the criteria.
    }
    @Test
    void testSearchRecipes_ExcludedIngredientAndInstructionKeyword_CorrectRecipeReturned() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        Recipe recipe2 = new Recipe("2", "Chicken Stir Fry", false, 3, Arrays.asList("chicken", "vegetables", "soy sauce"), "Stir-fry chicken and vegetables. Add soy sauce. Serve hot.");
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> result = recipeService.searchRecipes(new SearchCriteria(false, 0, "chicken", "stir-fry", "chicken"));

        assertThat(result).containsExactly(recipe2); // Expects recipe2 to be returned as it matches the criteria.
    }
}
