package com.gamechangers.android.asklocals.endtoend.espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.common.truth.Truth.assertThat;

import android.app.Instrumentation;
import android.content.Context;
import android.os.SystemClock;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.gamechangers.android.asklocals.MainActivity;
import com.gamechangers.android.asklocals.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Espresso tests for the Android app, which exercise the end-to-end app flow
 * involving the native Android view.
 */

@RunWith(AndroidJUnit4.class)
public final class AskLocalsLandingPageButtonsVisibilityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityRule =
            new IntentsTestRule<>(MainActivity.class);

    /**
     * The test first clicks on the Android view, launches the app, and then
     * asserts on the following.
     *
     * 1) App name "AskLocals" is visible.
     * 2) "Map" overlay is visible.
     * 3) "Reward advisor" button is visible.
     */
    @Test
    public void testLandingPageButtonsVisible() {
        assertThat(activityRule.getActivity()).isNotNull();

        SystemClock.sleep(10000);

        onView(withText(R.string.app_name)).check(matches(isDisplayed()));

        onView(withId(R.id.map)).check(matches(isDisplayed()));
        onView(withId(R.id.reward_button)).check(matches(isDisplayed()));
    }

}
