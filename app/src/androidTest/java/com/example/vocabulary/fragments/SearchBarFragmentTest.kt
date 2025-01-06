package com.example.vocabulary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.vocabulary.R
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class SearchBarFragmentTest {

    private lateinit var scenario: FragmentScenario<SearchBarFragment>
    private val searchBarFragment = SearchBarFragment()
    private lateinit var viewModel: ItemViewModel

    @Before
    fun init() {
        val bundle = Bundle()
        scenario =
            launchFragmentInContainer<SearchBarFragment>(
                bundle,
                R.style.Theme_Vocabulary,
                Lifecycle.State.RESUMED,
                object :
                    FragmentFactory() {
                    override fun instantiate(
                        classLoader: ClassLoader,
                        className: String
                    ): Fragment {
                        return searchBarFragment
                    }
                })
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isButtonFunctional() {
        Espresso.onView(withId(R.id.searchBtn)).check(ViewAssertions.matches(isClickable()))
    }
}