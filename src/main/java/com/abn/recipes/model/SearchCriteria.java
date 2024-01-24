package com.abn.recipes.model;

public class SearchCriteria {

    private boolean vegetarian;
    private int servings;
    private String ingredient;
    private String excludedIngredient;
    private String instructionKeyword;

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getExcludedIngredient() {
        return excludedIngredient;
    }

    public void setExcludedIngredient(String excludedIngredient) {
        this.excludedIngredient = excludedIngredient;
    }

    public String getInstructionKeyword() {
        return instructionKeyword;
    }

    public void setInstructionKeyword(String instructionKeyword) {
        this.instructionKeyword = instructionKeyword;
    }

}

