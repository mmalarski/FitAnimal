package com.example.fitanimal

import android.Manifest
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import androidx.test.rule.GrantPermissionRule

class BluetoothScreenTest {

    @get:Rule
    var scenarioRule = activityScenarioRule<BluetoothScreen>()

    @get:Rule
    var permissionCamera = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun testActivityCreate() {
        val scenario = scenarioRule.scenario
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun goToDeviceListTest() {
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button_scan)).check(matches(isDisplayed()))
    }

    @Test
    fun goToInsecureTest() {
        onView(withId(R.id.button7)).perform(click())
        onView(withId(R.id.button_scan)).check(matches(isDisplayed()))
    }
}