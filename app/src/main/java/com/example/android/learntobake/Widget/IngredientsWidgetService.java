package com.example.android.learntobake.Widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.android.learntobake.Layouts.MainActivity;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Creator(this.getApplicationContext(), intent);
    }

    class Creator implements RemoteViewsService.RemoteViewsFactory {
        private RecipeItem recipeItem;
        private Context context;

        public Creator(Context context, Intent intent) {
            this.context = context;
            this.recipeItem = intent.getParcelableExtra(BakeWidgetService.KEY_WIDGET_RECIPE_ITEM);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            this.recipeItem = BakeWidgetProvider.getRecipeItem();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (recipeItem != null) {
                return recipeItem.getIngredients().size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
            if (recipeItem != null) {
                remoteViews.setTextViewText(R.id.ingredient_view_amount_widget, String.valueOf(recipeItem.getIngredients().get(position).getQuantity()));
                remoteViews.setTextViewText(R.id.ingredient_view_measurement_widget, recipeItem.getIngredients().get(position).getMeasure());
                remoteViews.setTextViewText(R.id.ingredient_view_name_widget, recipeItem.getIngredients().get(position).getName());
            }
            remoteViews.setOnClickFillInIntent(R.id.ingredient_view_name, new Intent());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
