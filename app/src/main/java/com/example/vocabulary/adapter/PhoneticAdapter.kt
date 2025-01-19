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

    companion object {
        var phoneticText = ""
        var phoneticAudio = ""

        fun phoneticAdapter(listPhonetics: List<Phonetic>?): String {
            listPhonetics?.forEach { phonetic ->
                phonetic.text?.let { text ->
                    phonetic.audio?.let { audio ->
                        phoneticText = text
                        phoneticAudio = audio
                    }
                }
            }
            return phoneticText
        }

        fun playPronounce(iconPlay: ImageButton) {
            Log.i("Pronounce Audio:", phoneticAudio)
            Log.i("Pronounce Text:", phoneticText)

            iconPlay.setOnClickListener {
                if (phoneticAudio.isNotEmpty()) {
                    MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )
                        setDataSource(phoneticAudio)
                        prepare()
                        start()
                    }.let { mediaPlayer ->
                        iconPlay.setBackgroundResource(R.drawable.baseline_stop_24)
                        Timer().schedule(timerTask {
                            iconPlay.setBackgroundResource(R.drawable.baseline_play_arrow_24)
                        }, mediaPlayer.duration + 350L)
                    }
                }
            }
        }
    }
}

