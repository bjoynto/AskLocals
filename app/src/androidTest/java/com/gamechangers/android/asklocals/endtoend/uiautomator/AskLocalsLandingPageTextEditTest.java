package com.gamechangers.android.asklocals.endtoend.uiautomator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.gamechangers.android.asklocals.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * UiAutomator tests for the Android app, which exercise the end-to-end app flow
 * involving the native Android view.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AskLocalsLandingPageTextEditTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.gamechangers.android.asklocals";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "Nearby restaurant";

    private UiDevice mDevice;

    @Before
    public void startMainActivity() {
        mDevice = UiDevice.getInstance(getInstrumentation());

        mDevice.pressHome();

        final String launcherPackage = getLauncherPackage();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * The test first clicks on the Android view, launches the app, changes text in edit text box
     * and then asserts that the edit text box is populated with the new text.
     *
     */
    @Test
    public void testChangeTextSuccessful() {
        mDevice.findObject(By.text("Type your question..."))
                .setText(STRING_TO_BE_TYPED);

        UiObject2 changedText = mDevice
                .wait(Until.findObject(By.text(STRING_TO_BE_TYPED)),
                        5000 /* wait 500ms */);
        assertThat(changedText.getText(), is(equalTo(STRING_TO_BE_TYPED)));
    }

    private String getLauncherPackage() {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

}
