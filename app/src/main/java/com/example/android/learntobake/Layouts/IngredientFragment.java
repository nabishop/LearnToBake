package com.example.android.learntobake.Layouts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.android.learntobake.Adapters.RecipeDetailIngredientsAdapter;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

public class IngredientFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ingredients_activity, container, false);
        Bundle bundle = getArguments();
        RecipeItem recipeItem = bundle.getParcelable(RecipeDetailsOverviewFragment.getSavedInstanceRecipeKey());
        RecyclerView recyclerView = root.findViewById(R.id.ingredient_view_list_rv);
        RecipeDetailIngredientsAdapter recipeDetailIngredientsAdapter = new RecipeDetailIngredientsAdapter();
        recipeDetailIngredientsAdapter.setIngredients(recipeItem.getIngredients());
        recyclerView.setAdapter(recipeDetailIngredientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return root;
    }
}
