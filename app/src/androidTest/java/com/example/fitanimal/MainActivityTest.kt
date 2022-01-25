package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class MainActivityTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }
}