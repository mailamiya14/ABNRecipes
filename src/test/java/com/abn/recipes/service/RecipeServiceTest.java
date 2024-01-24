package com.abn.recipes.service;

import com.abn.recipes.model.Recipe;
import com.abn.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService = new RecipeServiceImpl();

    @Test
    void getAllRecipes() {
        // Mocking repository behavior
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(new Recipe()));

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(1, recipes.size());
    }

    // Add tests for other service methods
}
