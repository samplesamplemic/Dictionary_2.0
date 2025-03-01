package com.example.vocabulary.adapter

import android.media.MediaPlayer
import android.widget.ImageButton
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vocabulary.MainDispatcherRule
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.model.dto.Phonetic
import io.mockk.mockk
import io.mockk.mockkConstructor
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PhoneticAdapterTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var adapter: PhoneticAdapter
    private val phonetics = WordBuilder.defaultWord()[0].phonetics
    private lateinit var mockButton: ImageButton
    private lateinit var mockMediaPlayer: MediaPlayer

    @Before
    fun setUp() {
        adapter = PhoneticAdapter()
        mockButton = mockk(relaxed = true) // Mock ImageButton
        mockMediaPlayer = mockk(relaxed = true) // Mock MediaPlayer
        mockkConstructor(MediaPlayer::class)
    }

    @Test
    fun `getPhoneticText should return first valid phonetic`() = runTest {
        val phonetics = listOf(
            Phonetic("", ""),
            Phonetic("/test/", "https://audio.url/test.mp3"),
            Phonetic("/other/", "https://audio.url/other.mp3")
        )

        val result = adapter.getPhoneticText(phonetics)

        assertEquals("/test/", result.first)
        assertEquals("https://audio.url/test.mp3", result.second)
    }
}