/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gauravtak.assignment_list_design;

import android.view.View;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static com.gauravtak.assignment_list_design.TestUtils.withRecyclerView;


import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.gauravtak.assignment_list_design.views.presentation.list_screen.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class showcasing some {@link RecyclerViewActions} from Espresso.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecyclerViewTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
        new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void itemClickOfRecyclerView() {
            //wait for 5 seconds
        onView(isRoot()).perform(waitFor(5000));

        //checking the click listener sample test , other tests and UI automation testing can be implemented as per need
       //click on recyclerView Item can be tested with multiple items clicks or randomize clicks too as per the need
        onView(withRecyclerView(R.id.recyclerView).atPosition(1)).perform(click());

        }

    public ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}