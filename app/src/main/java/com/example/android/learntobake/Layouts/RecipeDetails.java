package com.example.android.learntobake.Layouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.learntobake.Adapters.RecipeDetailStepsAdapter;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.Models.Step;
import com.example.android.learntobake.R;
import com.example.android.learntobake.Widget.BakeWidgetService;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity implements RecipeDetailStepsAdapter.RecipesDetailStepsAdapterOnClickHandler {
    private static final String RECIPE_INTENT_KEY = MainActivity.getRecipeIntentKey();
    private static final String RECIPE_BUNDLE_KEY = "saved_recipe";
    public static final String RECIPE_STEPS_FRAGMENT_BUNDLE_KEY = "bundle_steps";
    public static final String RECIPE_STEP_FRAGMENT_NUMBER_KEY = "bundle_step_number";
    public static final String TAG_RECIPE_DETAILS_OVERVIEW_FRAGMENT = "recipe-details-overview-fragment";
    public static final String TAG_RECIPE_DETAILS_STEP_FRAGMENT = "recipe-details-step-fragment";

    private RecipeItem currentRecipe;
    private int stepIndex;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_BUNDLE_KEY, currentRecipe);
        outState.putInt(RECIPE_STEP_FRAGMENT_NUMBER_KEY, stepIndex);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Log.d("Recipe Details Saved", "SAVED INSTANCE STATE " + savedInstanceState);

        if (savedInstanceState == null) {
            currentRecipe = getIntent().getExtras().getParcelable(RECIPE_INTENT_KEY);
            stepIndex = 0;
        } else {
            currentRecipe = savedInstanceState.getParcelable(RECIPE_BUNDLE_KEY);
            stepIndex = savedInstanceState.getInt(RECIPE_STEP_FRAGMENT_NUMBER_KEY);
        }
        // Widget Update
        BakeWidgetService.startUpdateService(getApplicationContext(), currentRecipe);

        setUpFragmentOnCreate();
    }


    private void setUpFragmentOnCreate() {
        setTitle(currentRecipe.getName());

        if (getSupportFragmentManager().findFragmentByTag(TAG_RECIPE_DETAILS_OVERVIEW_FRAGMENT) == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_INTENT_KEY, currentRecipe);
            RecipeDetailsOverviewFragment recipeDetailsOverviewFragment = new RecipeDetailsOverviewFragment();
            recipeDetailsOverviewFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, recipeDetailsOverviewFragment, TAG_RECIPE_DETAILS_OVERVIEW_FRAGMENT)
                    .commit();
        }
        if (getResources().getBoolean(R.bool.isTablet)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RECIPE_STEPS_FRAGMENT_BUNDLE_KEY, currentRecipe.getSteps());
            bundle.putInt(RECIPE_STEP_FRAGMENT_NUMBER_KEY, stepIndex);
            RecipeDetailsStepFragment recipeDetailsStepFragment = new RecipeDetailsStepFragment();
            recipeDetailsStepFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_steps_container, recipeDetailsStepFragment, TAG_RECIPE_DETAILS_STEP_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStepClick(ArrayList<Step> stepsList, int stepIndex) {
        if (currentRecipe != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RECIPE_STEPS_FRAGMENT_BUNDLE_KEY, stepsList);
            System.out.println("Step index in Details " + stepIndex);
            this.stepIndex = stepIndex;
            bundle.putInt(RECIPE_STEP_FRAGMENT_NUMBER_KEY, stepIndex);
            if (getResources().getBoolean(R.bool.isTablet)) {
                RecipeDetailsStepFragment recipeDetailsStepFragment = new RecipeDetailsStepFragment();
                recipeDetailsStepFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_steps_container,
                        recipeDetailsStepFragment, TAG_RECIPE_DETAILS_STEP_FRAGMENT).commit();
            } else {
                RecipeDetailsStepFragment recipeDetailsStepFragment = new RecipeDetailsStepFragment();
                recipeDetailsStepFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, recipeDetailsStepFragment, TAG_RECIPE_DETAILS_STEP_FRAGMENT)
                        .addToBackStack("new step").commit();
            }
        }
    }


}
