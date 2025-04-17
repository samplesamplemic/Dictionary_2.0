package com.example.dictionary.handler

import android.os.SystemClock
import android.view.View
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Test

class SafeClickListenerTest {

    private lateinit var viewMock: View
    private var clickCount = 0
    private lateinit var safeClickListener: SafeClickListener

    @Before
    fun setUp() {
        viewMock = mockk(relaxed = true)
        clickCount = 0
        safeClickListener = SafeClickListener(
            defaultInterval = 1000,
            onSafeClick = { clickCount++ }
        )
        mockkStatic(SystemClock::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(SystemClock::class)
    }

    @Test
    fun testSafeClickListener() {
        // Mock elapsedRealtime to return different values for each call
        every { SystemClock.elapsedRealtime() } returnsMany listOf(1000L, 1500L, 2500L)

        // Simulate clicks
        safeClickListener.onClick(viewMock) // First click
        safeClickListener.onClick(viewMock) // Second click, within interval, should be ignored
        safeClickListener.onClick(viewMock) // Third click, outside interval, should be registered

        // Verify that onSafeClick was called twice
        assert(clickCount == 2)
    }
}
