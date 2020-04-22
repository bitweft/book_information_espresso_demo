package com.bookinformation

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.bookinformation.activities.BookDetailActivity
import com.example.bookinformation.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookDetailActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(BookDetailActivity::class.java, true, false)

    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra("bookId", "OL6800755M")
        intent.putExtra("setIdlingResource", true)

        activityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(activityRule.activity.idlingResource)
    }

    @Test
    fun checkBookTitleIsDisplayed() {
        onView(withId(R.id.title)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(activityRule.activity.idlingResource)
    }
}
