package com.example.cardiacmonitor;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RecordsActivityUiTest {
    @Rule
    public ActivityScenarioRule<RecordsActivity> activityRule =
            new ActivityScenarioRule<>(RecordsActivity.class);

    /**
     * this method is to check if upddte and delete button works properly
     * this method also checks if record is updated properly after updating all the fields
     */
    @Test
    public void testListViewAndUpdate()
    {
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
//        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(1).
//                check(matches((withText("Systol"))));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click());
        onView(withId(R.id.action_popUp)).check(matches(isDisplayed()));
        onView(withId(R.id.update)).perform(click());
        onView(withId(R.id.popUpDialog)).check(matches(isDisplayed()));
        onView(withId(R.id.no_btn)).perform(click());
        onView(withId(R.id.action_popUp)).check(matches(isDisplayed()));
        onView(withId(R.id.update)).perform(click());
        onView(withId(R.id.systolic)).perform(ViewActions.typeText("100"));
        onView(withId(R.id.diastolic)).perform(ViewActions.typeText("60"));
        onView(withId(R.id.pulse_rate)).perform(ViewActions.typeText("55"));

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        String date_v = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time_v = simpleDateFormat.format(calendar.getTime());

        onView(withId(R.id.comments)).perform(ViewActions.typeText("Sitting"));
        onView(withText(date_v)).check(matches(isDisplayed()));
        onView(withText(time_v)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.yes_btn)).perform(click());
    }

    /**
     * this method is to check if a record from the list is deleted properly
     */
    @Test
    public void testListAndDelete()
    {
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click());
        onView(withId(R.id.action_popUp)).check(matches(isDisplayed()));
        onView(withId(R.id.delete)).perform(click());
    }



}
