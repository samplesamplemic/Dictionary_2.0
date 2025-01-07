package com.example.vocabulary.handler

import android.os.SystemClock
import android.view.View

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