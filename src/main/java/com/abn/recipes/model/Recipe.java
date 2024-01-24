package com.abn.recipes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "recipe")
public class Recipe {

    @Id
    private String id;

    private String name;
    private boolean vegetarian;
    private int servings;
    private String instructions;
    private List<String> ingredients;

    public Recipe(String id, String name, boolean vegetarian, int servings, List<String> ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe() {

    }
}
