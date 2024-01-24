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

}
