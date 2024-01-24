package com.abn.recipes.model;

import lombok.Getter;

@Getter
public class SearchCriteria {

    private boolean vegetarian;
    private int servings;
    private String ingredient;
    private String excludedIngredient;
    private String instructionKeyword;

    public SearchCriteria(boolean vegetarian, int servings, String ingredient, String excludedIngredient, String instructionKeyword) {
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.ingredient = ingredient;
        this.excludedIngredient = excludedIngredient;
        this.instructionKeyword = instructionKeyword;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setExcludedIngredient(String excludedIngredient) {
        this.excludedIngredient = excludedIngredient;
    }

    public void setInstructionKeyword(String instructionKeyword) {
        this.instructionKeyword = instructionKeyword;
    }

}

