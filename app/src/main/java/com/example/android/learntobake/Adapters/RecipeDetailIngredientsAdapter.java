package com.example.android.learntobake.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.learntobake.Models.Ingredient;
import com.example.android.learntobake.R;

import java.util.ArrayList;

public class RecipeDetailIngredientsAdapter extends RecyclerView.Adapter<RecipeDetailIngredientsAdapter.RecipesDetailIngredientAdapterViewHolder> {
    private ArrayList<Ingredient> ingredients;

    @NonNull
    @Override
    public RecipesDetailIngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_individual_view, parent, false);
        return new RecipesDetailIngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesDetailIngredientAdapterViewHolder holder, int position) {
        holder.ingredientNameTextView.setText(ingredients.get(position).getName());
        holder.ingrdientQuantityTextView.setText(String.valueOf(ingredients.get(position).getQuantity()));
        holder.ingrdientMeasurementTextView.setText(ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) {
            return ingredients.size();
        }
        return 0;
    }

    public class RecipesDetailIngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientNameTextView;
        TextView ingrdientQuantityTextView;
        TextView ingrdientMeasurementTextView;

        public RecipesDetailIngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_view_name);
            ingrdientQuantityTextView = itemView.findViewById(R.id.ingredient_view_amount);
            ingrdientMeasurementTextView = itemView.findViewById(R.id.ingredient_view_measurement);
        }
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
