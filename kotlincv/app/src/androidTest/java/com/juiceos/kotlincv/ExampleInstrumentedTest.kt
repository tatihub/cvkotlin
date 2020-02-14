package com.juiceos.kotlincv

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.juiceos.kotlincv", appContext.packageName)
    }

    @Test
    fun user_can_query_cv(){

        loadMainActivity()
        onView(withId(R.id.mainActivityCvSearchText)).perform(typeText("creator"))
        Intents.release()

    }

    @Test
    fun user_can_click_search_button(){

        loadMainActivity()
        onView(withId(R.id.mainActivityCvSearchButton)).perform(click())
        Intents.release()

    }

    @Test
    fun check_we_have_progress_bar(){

        loadMainActivity()
        onView(withId(R.id.mainActivityProgress)).check(matches(anything()))
        Intents.release()

    }

    private fun loadMainActivity() {

        // espresso test to see if there is a search text field and it can be populated.
        val intentRule = IntentsTestRule<MainActivity>(
            MainActivity::class.java
        )
        intentRule.launchActivity(Intent())

    }


}
