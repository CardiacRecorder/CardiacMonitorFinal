package com.example.cardiacmonitor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(JUnit4.class)
@LargeTest
public class HomeActivityUiTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    /**
     * this method checks if proper screen is displayed by matching the top screen unique text
     */
    @Test
    public void testActivityName()
    {
        onView(withText("Home")).check(matches(isDisplayed()));
    }

    /**
     *this method is for checking add button and if progress bar is working properly after adding a record
     */
    @Test
    public void testAddButton()
    {

        onView(withId(R.id.add)).perform(click());//click on add button
        onView(withId(R.id.popUpDialog)).check(matches(isDisplayed()));
        onView(withId(R.id.systolic)).perform(ViewActions.typeText("120"));//enter systol value
        onView(withId(R.id.diastolic)).perform(ViewActions.typeText("80"));//enter diastol value
        onView(withId(R.id.pulse_rate)).perform(ViewActions.typeText("65"));//enter pulse rate value

        Calendar calendar = Calendar.getInstance();/* used to get a calendar using the current time zone and locale of the system*/
        Date currentDate = calendar.getTime();/*this returns a Date object that represents this Calendar's time value.*/
        String date_v = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);/*to get the date in given format and fill the date from system*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");/*to get the time in given format*/
        String time_v = simpleDateFormat.format(calendar.getTime());/*fill the time from system*/

        onView(withId(R.id.comments)).perform(ViewActions.typeText("Resting"));
        onView(withText(date_v)).check(matches(isDisplayed()));
        onView(withText(time_v)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.yes_btn)).perform(click());
        onView(withId(R.id.status)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_counter)).check(matches(isDisplayed()));/*if progress bar is displayed properly*/
        onView(withId(R.id.progress)).check(matches(isDisplayed()));/*if progress is executing properly*/
    }

    /**
     * this method is to check record button
     * if works properly should swicth to record screen
     */
    @Test
    public void testRecordButton(){
        onView(withId(R.id.records)).perform(click());
        onView(withText("Records")).check(matches(isDisplayed()))
    }




}
