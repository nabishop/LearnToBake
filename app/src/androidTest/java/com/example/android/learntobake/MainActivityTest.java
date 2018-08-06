package com.example.android.learntobake;

import android.support.test.espresso.*;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.android.learntobake.Layouts.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressBackUnconditionally;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource idlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void checkRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipe_list)).perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(ViewMatchers.withText("Nutella Pie")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkCheckedBox() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,
                click()));
        Espresso.onView(ViewMatchers.withId(R.id.got_all_ingredients_checkbox)).perform(click()).
                check(ViewAssertions.matches(ViewMatchers.isChecked()));
        Espresso.onView(ViewMatchers.withId(R.id.got_all_ingredients_checkbox)).perform(click()).check(ViewAssertions.matches(ViewMatchers.isNotChecked()));
    }




    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }


}
