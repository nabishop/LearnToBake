package com.example.android.learntobake.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.learntobake.Models.Step;
import com.example.android.learntobake.R;

import java.util.ArrayList;

public class RecipeDetailStepsAdapter extends RecyclerView.Adapter<RecipeDetailStepsAdapter.RecipesDetailStepsAdapterViewHolder> {
    private ArrayList<Step> steps;
    private RecipesDetailStepsAdapterOnClickHandler clickHandler;

    @NonNull
    @Override
    public RecipesDetailStepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_individual_view, parent, false);
        return new RecipesDetailStepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesDetailStepsAdapterViewHolder holder, int position) {
        String numberConcat = position + ".";
        holder.stepNumberTextView.setText(numberConcat);
        holder.stepNameTextView.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (steps != null) {
            return steps.size();
        }
        return 0;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }


    public interface RecipesDetailStepsAdapterOnClickHandler {
        void onStepClick(ArrayList<Step> stepsList, int stepIndex);
    }

    public RecipeDetailStepsAdapter(RecipesDetailStepsAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public class RecipesDetailStepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepNumberTextView;
        TextView stepNameTextView;

        public RecipesDetailStepsAdapterViewHolder(View itemView) {
            super(itemView);
            stepNumberTextView = itemView.findViewById(R.id.step_view_step_number);
            stepNameTextView = itemView.findViewById(R.id.step_view_step_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onStepClick(steps, getAdapterPosition());
        }
    }
}
