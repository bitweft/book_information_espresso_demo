package com.bookinformation

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.bookinformation.activities.BookDetailActivity
import com.bookinformation.constants.Constants.OPEN_LIBRARY_HOST
import com.bookinformation.constants.Constants.OPEN_LIBRARY_PORT
import com.example.bookinformation.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.InetAddress


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookDetailActivityTest {
    private lateinit var server: MockWebServer

    @get:Rule
    val activityRule = ActivityTestRule(BookDetailActivity::class.java, true, false)

    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra("bookId", "OL6800755M")
        intent.putExtra("setIdlingResource", true)

        server = MockWebServer()
        server.enqueue(MockResponse().setBody(MockResponses.successResponse))
        server.start(InetAddress.getByName(OPEN_LIBRARY_HOST), OPEN_LIBRARY_PORT.toInt())
        server.url("/books/OL6800755M.json")

        activityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(activityRule.activity.idlingResource)
    }

    @Test
    fun checkBookTitleIsDisplayed() {
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("someTitle")))
        onView(withId(R.id.author)).check(matches(withText("someAuthor")))
        onView(withId(R.id.publish_date)).check(matches(withText("somePublishDate")))
        onView(withId(R.id.num_of_pages)).check(matches(withText("500")))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(activityRule.activity.idlingResource)
        server.shutdown()
    }

    object MockResponses {
        val successResponse = """
        {
            "title": "someTitle",
            "publish_date": "somePublishDate",
            "number_of_pages": 500,
            "by_statement": "someAuthor"
        }
    """.trimIndent()
    }
}
