package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Test

class MainActivityTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

//    @Test
//    fun wardrobeButtonTest() {
//        onView(withId(R.id.wardrobe)).check(matches(isDisplayed()))
//    }
}