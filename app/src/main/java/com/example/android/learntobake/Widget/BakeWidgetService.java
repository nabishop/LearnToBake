package com.example.android.learntobake.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.learntobake.Models.RecipeItem;

public class BakeWidgetService extends IntentService {
    public static final String ACTION_INTENT = "com.example.android.learntobake.action.update.widget";
    public static final String KEY_WIDGET_RECIPE_ITEM = "key-widget-recipe-item";
    private static final String CLASS_NAME = "BakeWidgetService";

    public BakeWidgetService() {
        super(CLASS_NAME);
    }

    public static void startUpdateService(Context context, RecipeItem recipeItem) {
        Intent intent = new Intent(context, BakeWidgetService.class);
        intent.putExtra(KEY_WIDGET_RECIPE_ITEM, recipeItem);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            RecipeItem recipeItem = intent.getParcelableExtra(KEY_WIDGET_RECIPE_ITEM);
            Intent updateWidget = new Intent(ACTION_INTENT);
            updateWidget.setAction(ACTION_INTENT);
            updateWidget.putExtra(KEY_WIDGET_RECIPE_ITEM, recipeItem);
            sendBroadcast(updateWidget);
        } else {
            System.out.println("Intent is null");
        }
    }
}
