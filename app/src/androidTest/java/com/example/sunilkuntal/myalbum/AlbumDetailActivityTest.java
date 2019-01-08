package com.example.sunilkuntal.myalbum;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sunilkuntal.myalbum.home.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@RunWith(AndroidJUnit4.class)
public class AlbumDetailActivityTest {

    @Rule
    public ActivityTestRule<AlbumDetailsActivity> activityActivityTestRule = new ActivityTestRule<AlbumDetailsActivity>(AlbumDetailsActivity.class);


    @Test
    public void testID_Is_Visible(){
        onView(withId(R.id.tvID)).check(matches(isDisplayed()));
    }
}
