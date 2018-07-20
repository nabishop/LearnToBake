package com.example.android.learntobake.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeItem implements Parcelable {
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

    protected RecipeItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.readArrayList(Ingredient.class.getClassLoader());
        this.steps = in.readArrayList(Ingredient.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public static final Parcelable.Creator<RecipeItem> CREATOR = new Parcelable.Creator<RecipeItem>() {

        @Override
        public RecipeItem createFromParcel(Parcel source) {
            return new RecipeItem(source);
        }

        @Override
        public RecipeItem[] newArray(int size) {
            return new RecipeItem[size];
        }
    };
}
