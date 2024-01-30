package com.abn.recipes.service;

import com.abn.recipes.exceptions.RecipeNotFoundException;
import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(new Recipe()));

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(1, recipes.size());
    }

    @Test
    void getRecipeById() {
        Recipe recipe = new Recipe("1", "Pasta", true, 4, Arrays.asList("pasta", "tomato"), "Cook pasta");
        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipeById("1");

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
    }

    @Test
    void addRecipe() {
        Recipe recipe = new Recipe("1", "Pasta", true, 4, Arrays.asList("pasta", "tomato"), "Cook pasta");
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe result = recipeService.addRecipe(recipe);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());
    }

    @Test
    void updateRecipe() {
        Recipe existingRecipe = new Recipe("1", "Pasta", true, 4, Arrays.asList("pasta", "tomato"), "Cook pasta");
        when(recipeRepository.findById("1")).thenReturn(Optional.of(existingRecipe));

        Recipe updatedRecipe = new Recipe("1", "Updated Pasta", true, 4, Arrays.asList("pasta", "tomato"), "Cook pasta");
        when(recipeRepository.save(updatedRecipe)).thenReturn(updatedRecipe);

        Recipe result = recipeService.updateRecipe(updatedRecipe);

        assertNotNull(result);
        assertEquals("Updated Pasta", result.getName());
    }

    @Test
    void deleteRecipe() {
        String recipeIdToDelete = "1";
        when(recipeRepository.existsById(recipeIdToDelete)).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(recipeIdToDelete);

        boolean result = recipeService.deleteRecipe(recipeIdToDelete);

        assertTrue(result);
        verify(recipeRepository, times(1)).existsById(recipeIdToDelete);
        verify(recipeRepository, times(1)).deleteById(recipeIdToDelete);
    }

    @Test
    void testSearchRecipes_Vegetarian() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        when(mongoTemplate.find(any(Query.class), eq(Recipe.class))).thenReturn(Collections.singletonList(recipe1));

        List<Recipe> result = recipeService.searchRecipes(new SearchCriteria(true, 0, null, null, null));

        assertThat(result).containsExactly(recipe1);
    }

    @Test
    void testSearchRecipes_EmptyResult() {
        when(mongoTemplate.find(any(Query.class), eq(Recipe.class))).thenReturn(Collections.emptyList());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.searchRecipes(new SearchCriteria(true, 0, "nonexistent", null, null)));
    }

    @Test
    void testSearchRecipes_ServingsAndIngredient() {
        Recipe recipe1 = new Recipe("1", "Vegetarian Pasta", true, 4, Arrays.asList("pasta", "tomatoes", "olive oil"), "Boil pasta. Mix tomatoes and olive oil. Toss with pasta.");
        Recipe recipe2 = new Recipe("2", "Chicken Stir Fry", false, 3, Arrays.asList("chicken", "vegetables", "soy sauce"), "Stir-fry chicken and vegetables. Add soy sauce. Serve hot.");
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        assertThrows(RecipeNotFoundException.class, () -> recipeService.searchRecipes(new SearchCriteria(false, 4, "rice", null, null)));
    }

}
