package com.example.vocabulary.adapter

import MeaningAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.databinding.ItemDefinitionBinding
import com.example.vocabulary.databinding.ItemPartOfSpeechBinding
import com.example.vocabulary.model.dto.Definition
import com.example.vocabulary.model.dto.Meaning
import com.example.vocabulary.viewModel.ItemViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@MediumTest
class MeaningAdapterInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var viewModel: ItemViewModel

    private lateinit var context: Context
    private lateinit var adapter: MeaningAdapter
    private val meanings = WordBuilder.defaultWord()[0].meanings

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        adapter = MeaningAdapter(meanings)
    }

    @Test
    fun onBindViewHolderShouldBind_PartOfSpeech_correctly() {
        val binding = ItemPartOfSpeechBinding.inflate(
            LayoutInflater.from(context),
            mockk<ViewGroup>(relaxed = true),
            false
        )

        val holder = MeaningAdapter.PartOfSpeechViewHolder(binding)
        holder.bind(meanings[0])

        assertEquals("Noun", binding.partOfSpeech.text.toString())
    }

    @Test
    fun onBindViewHolderShouldBind_Definition_correctly() {
        val binding = ItemDefinitionBinding.inflate(
            LayoutInflater.from(context),
            mockk<ViewGroup>(relaxed = true),
            false
        )

        val holder = MeaningAdapter.DefinitionViewHolder(binding)
        holder.bind("A test definition", "An example usage")

        assertEquals("A test definition", binding.definition.text.toString())
        assertEquals("• \"An example usage\"", binding.exampleText.text.toString())
    }

    @Test
    fun updateDateShould_refresh_dataset() {
        val newMeaning = listOf(
            Meaning(
                partOfSpeech = "adjective",
                definitions = listOf(
                    Definition("Description test", null)
                )
            )
        )
        adapter.updateData(newMeaning)

        assertEquals(2, adapter.itemCount)
    }
}