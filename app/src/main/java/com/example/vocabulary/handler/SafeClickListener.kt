package com.example.vocabulary.handler

import android.os.SystemClock
import android.view.View

/**
 * A click listener that prevents double click within a specified interval.
 *
 * @param defaultInterval The minimum interval between click in milliseconds.
 * @param onSafeClick The action to perform on a safe click.
 */
class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {
    @Volatile
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (isDoubleClick()) return
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeClick(v)
    }

    private fun isDoubleClick(): Boolean {
        return SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval
    }
}

/**
 * Extension function to set a safe click listener on a view.
 *
 * @param onSafeClick The action to perform on a safe click
 */
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}