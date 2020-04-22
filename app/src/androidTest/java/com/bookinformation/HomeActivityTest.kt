package com.bookinformation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.bookinformation.activities.HomeActivity
import com.example.bookinformation.R
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, true)

    @Test
    fun checkButtonDisabledOnLaunch() {
        onView(withId(R.id.search_books)).check(matches(not(isEnabled())))
    }
}