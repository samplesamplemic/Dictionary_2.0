package com.example.vocabulary.adapter

import MeaningAdapter
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vocabulary.MainDispatcherRule
import com.example.vocabulary.builder.WordBuilder
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MeaningAdapterTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var adapter: MeaningAdapter
    private val meanings = WordBuilder.defaultWord()[0].meanings

    @Before
    fun setUp() {
        adapter = MeaningAdapter(meanings)
    }

    @Test
    fun `getItemCount should return correct count`() = runTest {
        val expectedCount = 1 + meanings.sumOf { it.definitions.size }
        assertEquals(expectedCount, adapter.itemCount)
    }

    @Test
    fun `getItemViewType should return correct type`() = runTest {
        assertEquals(0, adapter.getItemViewType(0))
        assertEquals(1, adapter.getItemViewType(1))
    }
}