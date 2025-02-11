package com.example.vocabulary.adapter

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.widget.ImageButton
import com.example.vocabulary.R
import com.example.vocabulary.model.dto.Phonetic
import java.util.Timer
import kotlin.concurrent.timerTask

class PhoneticAdapter {

    fun getPhoneticText(listPhonetics: List<Phonetic>?): Pair<String, String?> {
        listPhonetics?.forEach { phonetic ->
            if (!phonetic.text.isNullOrEmpty() && !phonetic.audio.isNullOrEmpty()) {
                return Pair(phonetic.text, phonetic.audio)
            }
        }
        return Pair("", null)
    }

    fun playPronounce(iconPlay: ImageButton, audioUrl: String?) {
        if (audioUrl.isNullOrEmpty()) {
            Log.w("PhoneticAdapter", "No audio available")
            return
        }

        iconPlay.setOnClickListener {
            MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(audioUrl)
                prepare()
                start()

                iconPlay.setBackgroundResource(R.drawable.baseline_stop_24)
                Timer().schedule(timerTask {
                    iconPlay.setBackgroundResource(R.drawable.baseline_play_arrow_24)
                }, duration + 350L)

                setOnCompletionListener {
                    iconPlay.setBackgroundResource(R.drawable.baseline_play_arrow_24)
                    release()
                }

                setOnErrorListener { _, _, _ ->
                    Log.e("PhoneticAdapter", "Error playing audio")
                    release()
                    true
                }
            }
        }
    }
}

