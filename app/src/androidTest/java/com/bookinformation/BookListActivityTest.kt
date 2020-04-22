package com.bookinformation

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.bookinformation.activities.BookListActivity
import com.bookinformation.adapter.BookAdapter
import com.bookinformation.helpers.atPositionOnView
import com.example.bookinformation.R
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookListActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(BookListActivity::class.java, true, false)

    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra("bookName", "harry")
        intent.putExtra("setIdlingResource", true)

        activityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(activityRule.activity.idlingResource)
    }

    @Test
    fun checkDetailsOfFirstItemInBookResults() {
        onView(withId(R.id.books_list)).check(matches(isDisplayed()))
        onView(withId(R.id.books_list)).check(matches(atPositionOnView(0, R.id.title, withText(containsString("Harry")))))
        onView(withId(R.id.books_list)).check(matches(atPositionOnView(0, R.id.author, withText(containsString("Rowling")))))
        onView(withId(R.id.books_list)).check(matches(atPositionOnView(0, R.id.book_image, isDisplayed())))
    }

    @Test
    fun clickOnFirstItemInBookResults() {
        onView(withId(R.id.books_list)).perform(RecyclerViewActions.actionOnItemAtPosition<BookAdapter.BookHolder>(0, click()))
        onView(withId(R.id.publish_date)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(activityRule.activity.idlingResource)
    }
}
