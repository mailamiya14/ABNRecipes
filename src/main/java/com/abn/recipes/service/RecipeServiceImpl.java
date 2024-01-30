package com.abn.recipes.service;

import com.abn.recipes.exceptions.RecipeNotFoundException;
import com.abn.recipes.model.Recipe;
import com.abn.recipes.model.SearchCriteria;
import com.abn.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
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

        Criteria criteriaObject = new Criteria();

        if (criteria.isVegetarian()) {
            criteriaObject = criteriaObject.and("vegetarian").is(criteria.isVegetarian());
        }

        if (criteria.getServings() > 0) {
            criteriaObject = criteriaObject.and("servings").is(criteria.getServings());
        }

        if (criteria.getIngredient() != null) {
            criteriaObject = criteriaObject.and("ingredients").in(criteria.getIngredient());
        }

        if (criteria.getExcludedIngredient() != null) {
            criteriaObject = criteriaObject.and("ingredients").nin(criteria.getExcludedIngredient());
        }

        if (criteria.getInstructionKeyword() != null) {
            criteriaObject = criteriaObject.and("instructions").regex(criteria.getInstructionKeyword(), "i");
        }

        Query query = new Query(criteriaObject);

        // Execute the query and retrieve the results
        List<Recipe> result = mongoTemplate.find(query, Recipe.class);

        if (result.isEmpty()) {
            throw new RecipeNotFoundException("No Recipe found for the search criteria");
        }

        return result;
    }

}
