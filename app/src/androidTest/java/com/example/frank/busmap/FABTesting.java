package com.example.frank.busmap;

import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FABTesting {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testingFab() throws Exception{
        Espresso.onView(withId(R.id.fab_direction)).perform(click());
//        ViewInteraction viewGroup = Espresso.onView(withId(R.id.choice_A)).perform(isDisplayed());
        Espresso.onView(withId(R.id.busNumber)).perform(typeText("U3"));




//        mainActivityActivityTestRule.getActivity().onClick();

    }

}
