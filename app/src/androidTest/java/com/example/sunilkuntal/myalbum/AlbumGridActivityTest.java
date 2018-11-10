package com.example.sunilkuntal.myalbum;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.example.sunilkuntal.myalbum.ui.home.AlbumScreen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sunilkuntal.myalbum.home.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@RunWith(AndroidJUnit4.class)
public class AlbumGridActivityTest {

    @Rule
    public ActivityTestRule<AlbumGridActivity> activityActivityTestRule = new ActivityTestRule<AlbumGridActivity>(AlbumGridActivity.class);


    @Test
    public void testVisibleItemCount(){
        onView(withId(R.id.recView)).check(withItemCount(greaterThan(3)));
    }
}
