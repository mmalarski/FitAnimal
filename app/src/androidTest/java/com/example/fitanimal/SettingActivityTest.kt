package com.example.fitanimal

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SettingActivityTest {


    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule(SettingActivity::class.java)

    private var mActivity : SettingActivity? = null

    @Before
    fun setUp() {
        mActivity = mActivityTestRule.activity
    }

    @Test
    fun createTest() {
        val view : View = mActivity!!.findViewById(R.id.button)
        assertNotNull(view)
    }

    @Test
    fun goToSharingTest() {
        onView(withId(R.id.button4)).perform(click())
        onView(withId(R.id.scanner_view)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mActivity = null
    }
}