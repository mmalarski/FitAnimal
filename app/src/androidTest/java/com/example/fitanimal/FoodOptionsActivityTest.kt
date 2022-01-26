package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FoodOptionsActivityTest {
    @get:Rule
    var scenarioRule = activityScenarioRule<FoodOptionsActivity>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun testIncreaseDecrease() {
        onView(withId(R.id.plusButtonFood2)).perform(click())
        onView(withId(R.id.amountTextView)).check(matches(withText("Amount: 2")))

        onView(withId(R.id.minusButtonFood)).perform(click())
        onView(withId(R.id.amountTextView)).check(matches(withText("Amount: 1")))
    }

}