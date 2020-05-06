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
import com.bookinformation.configs.Configs.OPEN_LIBRARY_HOST
import com.bookinformation.configs.Configs.OPEN_LIBRARY_PORT
import com.bookinformation.helpers.atPositionOnView
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
class BookListActivityTest {
    private lateinit var server: MockWebServer

    @get:Rule
    val activityRule = ActivityTestRule(BookListActivity::class.java, true, false)

    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra("bookName", "someTitle1")
        intent.putExtra("setIdlingResource", true)

        server = MockWebServer()
        server.enqueue(MockResponse().setBody(MockResponses.searchResponse))
        server.enqueue(MockResponse().setBody(MockResponses.detailResponse))
        server.start(InetAddress.getByName(OPEN_LIBRARY_HOST), OPEN_LIBRARY_PORT.toInt())
        server.url("/")

        activityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(activityRule.activity.idlingResource)
    }

    @Test
    fun checkDetailsOfFirstItemInBookResults() {
        onView(withId(R.id.books_list)).check(matches(isDisplayed()))
        onView(withId(R.id.books_list)).check(matches(atPositionOnView(0, R.id.title, withText("someTitle1"))))
        onView(withId(R.id.books_list)).check(matches(atPositionOnView(0, R.id.author, withText("someAuthor1-1, someAuthor1-2"))))
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
        server.shutdown()
    }

    object MockResponses {
        val detailResponse = """
            {
               "title": "someTitle",
               "publish_date": "somePublishDate",
               "number_of_pages": 500,
               "by_statement": "someAuthor"
            }
        """.trimIndent()

        val searchResponse = """
            {
                "start": 0,
                "num_found": 6255,
                "numFound": 6255,
                "docs": [
                    {
                        "title": "someTitle1",
                        "author_name": [
                            "someAuthor1-1",
                            "someAuthor1-2"
                        ],
                        "cover_edition_key": "coverKey1"
                    },
                    {
                        "title": "someTitle2",
                        "author_name": [
                            "someAuthor2"
                        ],
                        "cover_edition_key": "coverKey2"
                    }
                ]
            }
        """.trimIndent()
    }
}
