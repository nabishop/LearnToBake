package com.example.android.learntobake.Layouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

public class RecipeDetails extends AppCompatActivity {
    private static final String RECIPE_INTENT_KEY = MainActivity.getRecipeIntentKey();
    private static final String RECIPE_BUNDLE_KEY = "saved_recipe";

    private RecipeItem currentRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (savedInstanceState == null) {
            currentRecipe = getIntent().getExtras().getParcelable(RECIPE_INTENT_KEY);
        } else {
            currentRecipe = savedInstanceState.getParcelable(RECIPE_BUNDLE_KEY);
        }
        setUpFragment();
    }

    private void setUpFragment() {
        setTitle(currentRecipe.getName());
    }
}
