package com.example.fitanimal

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class SettingActivityTest {


    @Rule(order = 0)
    @JvmField
    val mActivityTestRule = ActivityTestRule(SettingActivity::class.java)

    @get:Rule(order = 1)
    val intentRule = IntentsTestRule(SharingActivity::class.java)

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

    //TODO(cannot find button4, figure out why)
//    @Test
//    fun goToSharingTest() {
//        onView(withId(R.id.button4)).perform(click()) //to nie git
//        intended(hasComponent(SharingActivity::class.java.name)) //to git
//    }

    @After
    fun tearDown() {
        mActivity = null
    }
}