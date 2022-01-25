package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class FoodPopupTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<FoodPopup>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

}