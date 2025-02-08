package com.example.vocabulary.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.vocabulary.MainDispatcherRule
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.network.WordRetriever
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var itemViewModel: ItemViewModel
    private var wordRetriever: WordRetriever = mockk()
    private var observerSelectedItem: Observer<Resource<Word>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        // Initialize ViewModel and Observer
        itemViewModel = ItemViewModel(wordRetriever)
        itemViewModel.selectedItem.observeForever(observerSelectedItem)
    }

    @After
    fun tearDown() {
        itemViewModel.selectedItem.removeObserver(observerSelectedItem)
    }

    @Test
    fun `selectItem should update loading state and selectedItem`() = runTest {
        val word = "example"
        val expectedWord = WordBuilder.defaultWord()
        val expectedResult = Resource.Success(expectedWord)

        coEvery { wordRetriever.getData(word) } returns expectedResult //mockk

        itemViewModel.selectItem(word)
        advanceUntilIdle()

        coVerify {
            observerSelectedItem.onChanged(expectedResult)
        }
        assertEquals(expectedResult, itemViewModel.selectedItem.value)
    }
}