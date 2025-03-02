package com.example.vocabulary.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.ImageButton
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.vocabulary.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Ignore("Execution is too slow")
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@MediumTest
class PhoneticAdapterInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var adapter: PhoneticAdapter
    private lateinit var iconPlay: ImageButton
    private lateinit var mediaPlayer: MediaPlayer

    @Before
    fun setUp() {
        adapter = PhoneticAdapter()
        iconPlay = mockk(relaxed = true) // Mock ImageButton
        mediaPlayer = mockk(relaxed = true) // Mock MediaPlayer

        mockkStatic(MediaPlayer::class)
        every { MediaPlayer.create(any<Context>(), any<Uri>()) } returns mediaPlayer
        every { mediaPlayer.start() } just Runs
        every { mediaPlayer.setOnCompletionListener(any()) } just Runs
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun playPronounce_shouldPlayAudioAndChangeButtonIcon() {
        val audioUrl = "https://www.sound-example.com/test.mp3"

        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                adapter.playPronounce(iconPlay, audioUrl)
                iconPlay.performClick()
            }
        }

        val completionListenerSlot = slot<MediaPlayer.OnCompletionListener>()
        every { mediaPlayer.setOnCompletionListener(capture(completionListenerSlot)) } just Runs

        completionListenerSlot.captured.onCompletion(mediaPlayer)

//        verify { iconPlay.setBackgroundResource(R.drawable.baseline_stop_24) }
        verify { iconPlay.setOnClickListener(any()) }
    }

    @Test
    fun playPronounce_shouldNotCrash_whenAudioUrlIsNull() {
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                adapter.playPronounce(iconPlay, null)
                iconPlay.performClick()
            }
        }

//        verify { iconPlay.setBackgroundResource(R.drawable.baseline_play_arrow_24) }
        verify { iconPlay.setOnClickListener(any()) }
    }
}