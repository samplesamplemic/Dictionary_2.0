package com.example.vocabulary.fragments

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.vocabulary.R
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.launchFragmentInHiltContainer
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ProgressBarFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var viewModel: ItemViewModel

    private val selectedItemLiveData = MutableLiveData<Resource<Word>>()

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mockk(relaxed = true)
        every { viewModel.selectedItem } returns selectedItemLiveData
    }

    @Test
    fun testProgressBarVisibility() {
        val scenario =
            launchFragmentInHiltContainer<ProgressBarFragment> { }

        // Update LiveData states within the test
        selectedItemLiveData.postValue(Resource.Loading())
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        selectedItemLiveData.postValue(Resource.Success(WordBuilder.defaultWord()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }
}
