package com.example.android.learntobake.Layouts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.learntobake.Adapters.RecipeDetailIngredientsAdapter;
import com.example.android.learntobake.Adapters.RecipeDetailStepsAdapter;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

import butterknife.ButterKnife;

public class RecipeDetailsOverviewFragment extends Fragment {
    private RecipeDetailStepsAdapter stepsAdapter;

    private static final String SAVED_INSTANCE_RECIPE_KEY = "saved_recipe";

    private RecipeItem currentRecipe;

    public RecipeDetailsOverviewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        RecyclerView recyclerViewSteps = root.findViewById(R.id.steps_recycler_view);
        System.out.println("GET ACTIVITY " + getActivity());
        stepsAdapter = new RecipeDetailStepsAdapter((RecipeDetailStepsAdapter.RecipesDetailStepsAdapterOnClickHandler) getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSteps.setAdapter(stepsAdapter);
        recyclerViewSteps.setLayoutManager(linearLayoutManager);

        TextView ingredientTextView = root.findViewById(R.id.fragment_recipe_details_ingredient_text);
        ingredientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchIngredientsFragment();
            }
        });

        if (savedInstanceState != null) {
            currentRecipe = savedInstanceState.getParcelable(SAVED_INSTANCE_RECIPE_KEY);
        } else {
            currentRecipe = getArguments().getParcelable(MainActivity.getRecipeIntentKey());
        }

        stepsAdapter.setSteps(currentRecipe.getSteps());
        return root;
    }

    private void launchIngredientsFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_INSTANCE_RECIPE_KEY, currentRecipe);
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.recipe_detail_container, ingredientFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack("Recipe_Details_Overview_Fragment");
    }

    public static String getSavedInstanceRecipeKey() {
        return SAVED_INSTANCE_RECIPE_KEY;
    }
}
