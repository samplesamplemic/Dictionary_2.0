package com.example.vocabulary.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.network.WordRetriever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ItemViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var itemViewModel: ItemViewModel
    private var testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var wordRetriever: WordRetriever

    @Mock
    private lateinit var observerSelectedItem: Observer<Resource<Word>>

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        itemViewModel = ItemViewModel(wordRetriever)
        itemViewModel.selectedItem.observeForever(observerSelectedItem)
    }

    @After
    fun tearDown() {
        closeable.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `selectItem should update loading state and selectedItem`() = runTest {
        val word = "example";
        val expectedWord = WordBuilder.defaultWord();
        val expectedResult = Resource.Success(expectedWord)

        Mockito.`when`(wordRetriever.getData(word)).thenReturn(expectedResult)

        itemViewModel.selectItem(word)

        testDispatcher.scheduler.advanceUntilIdle()
        Mockito.verify(observerSelectedItem).onChanged(expectedResult)
        assertEquals(expectedResult, itemViewModel.selectedItem.value)
    }
}