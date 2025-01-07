package com.example.vocabulary.handler

import android.os.SystemClock
import android.view.View
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SafeClickListenerTest {

    private lateinit var viewMock: View
    private var clickCount = 0
    private lateinit var safeClickListener: SafeClickListener
    private lateinit var mockedStatic: MockedStatic<SystemClock>

    @Before
    fun setUp() {
        viewMock = mock(View::class.java)
        clickCount = 0
        safeClickListener = SafeClickListener(
            defaultInterval = 1000,
            onSafeClick = { clickCount++ }
        )
        mockedStatic = Mockito.mockStatic(SystemClock::class.java)
    }

    @After
    fun tearDown() {
        mockedStatic.close()
    }

    @Test
    fun testSafeClickListener() {
        // Mock elapsedRealtime to return different values for each call
        mockedStatic.`when`<Long> { SystemClock.elapsedRealtime() }.thenReturn(1000L, 1500L, 2500L)

        // Simulate clicks
        safeClickListener.onClick(viewMock) // First click
        safeClickListener.onClick(viewMock) // Second click, within interval, should be ignored
        safeClickListener.onClick(viewMock) // Third click, outside interval, should be registered

        // Verify that onSafeClick was called twice
        assert(clickCount == 2)
    }
}
