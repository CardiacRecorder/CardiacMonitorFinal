package com.example.cardiacmonitor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest

public class SplashScreenActivityUiTest {

    @Rule
    public ActivityScenarioRule<SplashScreenActivity> activityRule =
            new ActivityScenarioRule<>(SplashScreenActivity.class);

    /**
     * this method is for checking the intent of splashscreen
     */
    @Test
    public void testSplashScreenText()
    {
        onView(withText("Cardiac Monitor")).check(matches(isDisplayed()));
        onView(withId(R.id.gifImageView)).check(matches(isDisplayed()));/*to check the splashscreen image*/
        onView(withText("know your heart rate quickly")).check(matches(isDisplayed()));
        onView(withText("Developed")).check(matches(isDisplayed()));
        onView(withText("by")).check(matches(isDisplayed()));
        onView(withText("Rupok | Sadman")).check(matches(isDisplayed()));/*to check the developers names*/
    }





}
