package com.example.fitanimal

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharingActivityTest {
    @get:Rule
    var scenarioRule = activityScenarioRule<SharingActivity>()

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    //TODO(test for onRequestPermissionsResult with toast matcher)
}