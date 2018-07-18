package com.example.android.learntobake.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeItem implements Serializable {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String image;

    public RecipeItem(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps,
                      int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String recipe = "RECIPE INFO : ID: " + id + ", NAME: " + name + ", INGREDIENTS: " + ingredients +
                ", STEPS: " + steps + ", SERVINGS: " + servings + ", IMAGE: " + image;
        return recipe;
    }
}
