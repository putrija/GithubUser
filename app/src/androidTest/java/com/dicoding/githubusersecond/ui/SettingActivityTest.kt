package com.dicoding.githubusersecond.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.githubusersecond.R

@RunWith(AndroidJUnit4::class)
class SettingActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun testSwitchTheme() {
        onView(withId(R.id.switch_theme)).perform(click())
        onView(withId(R.id.switch_theme)).check(matches(isChecked()))
        onView(withId(R.id.switch_theme)).perform(click())
        onView(withId(R.id.switch_theme)).check(matches(isNotChecked()))
    }
}
