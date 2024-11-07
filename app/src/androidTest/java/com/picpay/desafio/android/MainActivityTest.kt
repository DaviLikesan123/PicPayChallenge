package com.picpay.desafio.android

import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.activity.MainActivity
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkActivityVisibility() { onView(withId(R.layout.activity_main)).check(matches(isDisplayed())) }

    @Test
    fun recycleViewTest() {
        val viewGroup = onView(
            Matchers.allOf(
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(R.id.recyclerView),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ViewGroup::class.java))
                    )
                ), isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))
    }
}



