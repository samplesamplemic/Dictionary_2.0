package com.example.vocabulary.adapter

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.widget.ImageButton
import com.example.vocabulary.R
import com.example.vocabulary.model.dto.Phonetics
import java.util.*
import kotlin.concurrent.timerTask

class PhoneticAdapter {

    companion object {

        fun phoneticAdapter(itemToFind: List<Phonetics>?): String {
            var itemFound: String = ""
            if (itemToFind != null) {

                run outer@{
                    for (element in itemToFind) {
                        itemFound = element.text.toString()
                        Log.i("Parser: ", itemFound.toString() + itemToFind.toString())
                        if (itemFound != "null") {
                            return@outer
                        }
                    }
                }
            }
            return itemFound
        }

        fun playPronounce(itemToFind: List<Phonetics>?, iconPlay: ImageButton) {

            val listAudio: MutableList<String?> = mutableListOf()
            var wordAudio: String? = ""
            val mediaPlayer: MediaPlayer = MediaPlayer()
            var pause: Boolean = true

            if (itemToFind != null) {
                for (element in itemToFind) {
                    wordAudio = element.audio.toString()
                    if (wordAudio != "") {
                        listAudio.add(wordAudio)
                    }
                    print(listAudio)
                }
            }

            mediaPlayer.setAudioAttributes(
                AudioAttributes
                    .Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            iconPlay.setOnClickListener {
                if (pause) {
                    mediaPlayer.reset();
                    println(listAudio[0])
                    mediaPlayer.setDataSource(listAudio[0])
                    mediaPlayer.prepare()
                    mediaPlayer.start()

                    iconPlay.setBackgroundResource(R.drawable.baseline_stop_24)
                    Timer().schedule(timerTask {
                        iconPlay.setBackgroundResource(R.drawable.baseline_play_arrow_24)
                    }, mediaPlayer.duration.toLong() + 350)
                }
            }
        }
    }
}
