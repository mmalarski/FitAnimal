package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class WardrobePopupTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<WardrobePopup>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }
}