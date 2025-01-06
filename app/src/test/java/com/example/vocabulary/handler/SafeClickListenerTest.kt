package com.example.vocabulary.handler

import android.os.SystemClock
import android.view.View
import androidx.constraintlayout.utils.widget.MockView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SafeClickListenerTest {
    private lateinit var mockView: View
    private lateinit var safeClickListener: SafeClickListener

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockView = Mockito.mock(View::class.java)
        safeClickListener = SafeClickListener(defaultInterval = 1000) { }
    }

    @Test
    fun testClickWithInterval() {
        val mockOnSafeClick = Mockito.mock(View.OnClickListener::class.java)
        safeClickListener = SafeClickListener(1000) {
            mockOnSafeClick.onClick(mockView)
        }

        safeClickListener.onClick(mockView)
        verify(mockOnSafeClick, times(1)).onClick(mockView)

        SystemClock.setCurrentTimeMillis(SystemClock.elapsedRealtime() + 500)
        safeClickListener.onClick(mockView)
        verify(mockOnSafeClick, times(1)).onClick(mockView)

        SystemClock.setCurrentTimeMillis(SystemClock.elapsedRealtime() + 1500)
        safeClickListener.onClick(mockView)
        verify(mockOnSafeClick, times(2)).onClick(mockView)
    }
}