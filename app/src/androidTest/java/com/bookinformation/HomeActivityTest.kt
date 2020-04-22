package com.bookinformation

import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.bookinformation.activities.HomeActivity
import com.example.bookinformation.R
import org.hamcrest.CoreMatchers.allOf
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
    fun checkSearchButtonIsDisabledWhenNoText() {
        onView(withId(R.id.search_books)).check(matches(not(isEnabled())))

        onView(withId(R.id.book_name)).perform(typeText("harry"))
        onView(withId(R.id.search_books)).check(matches(isEnabled()))

        onView(withId(R.id.book_name)).perform(replaceText(""))
        onView(withId(R.id.search_books)).check(matches(not(isEnabled())))
    }

    @Test
    fun textOnHomeScreenShouldBeCorrect() {
        onView(withId(R.id.book_name)).check(matches(withHint("Book Name")))
        onView(withId(R.id.search_books)).check(matches(withText("SEARCH BOOKS")))
    }

    @Test
    fun textOnToolbarShouldBeCorrect() {
        onView(
            allOf(
                isAssignableFrom(AppCompatTextView::class.java),
                withParent(isAssignableFrom(Toolbar::class.java))
            )
        ).check(matches(withText("World of Books")))
    }

    @Test
    fun checkThatBookSearchInputIsFocused() {
        onView(withId(R.id.book_name)).check(matches(hasFocus()))
    }

    @Test
    fun checkSearchResultsShownOnClickingSearchButton() {
        onView(withId(R.id.book_name)).perform(typeText("harry"))
        onView(withId(R.id.search_books)).perform(click())

        onView(withId(R.id.books_list)).check(matches(isDisplayed()))
    }
}