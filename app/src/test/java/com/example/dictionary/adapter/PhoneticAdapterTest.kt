package com.example.dictionary.adapter

import android.media.MediaPlayer
import android.util.Log
import android.widget.ImageButton
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dictionary.MainDispatcherRule
import com.example.dictionary.model.dto.Phonetic
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
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
    private lateinit var mockButton: ImageButton
    private lateinit var mockMediaPlayer: MediaPlayer

    @Before
    fun setUp() {
        adapter = PhoneticAdapter()
        mockButton = mockk(relaxed = true) // Mock ImageButton
        mockMediaPlayer = mockk(relaxed = true) // Mock MediaPlayer
        mockkConstructor(MediaPlayer::class)
        mockkStatic(Log::class)
        every { Log.w(any<String>(), any<String>()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
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

    @Test
    fun `playPronounce should not play if audioUrl is null or empty`() {
        adapter.playPronounce(mockButton, null)
        adapter.playPronounce(mockButton, "")

        verify(exactly = 0) { mockButton.setOnClickListener(any()) }

        // Confirm that no unexpected interactions happened
        confirmVerified(mockButton)
    }

    @Test
    fun `playPronounce should play audio when URL is valid`() {
        val audioUrl = "https://audio.url/test.mp3"

        every { anyConstructed<MediaPlayer>().setDataSource(audioUrl) } just Runs
        every { anyConstructed<MediaPlayer>().prepare() } just Runs
        every { anyConstructed<MediaPlayer>().start() } just Runs
        every { anyConstructed<MediaPlayer>().release() } just Runs

        adapter.playPronounce(mockButton, audioUrl)

        verify { mockButton.setOnClickListener(any()) }
    }
}