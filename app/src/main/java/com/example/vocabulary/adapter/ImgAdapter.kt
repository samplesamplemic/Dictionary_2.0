package com.example.vocabulary.adapter

import android.graphics.BitmapFactory
import android.util.Log
import com.example.vocabulary.R
import com.example.vocabulary.databinding.ImgScraperFragmentBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class ImgAdapter {

    companion object {
        var linkImgToDisplay = ""

        fun getImage(wordToScrap: String): String {
            println(wordToScrap)
            val url = "https://en.wikipedia.org/wiki/$wordToScrap"
            val doc = Jsoup.connect(url).get()
            Log.i("test2Scraper ", "" + doc.title() + linkImgToDisplay)

            val scrapingImages: Elements = doc.select("img")

            run outer@{
                scrapingImages.forEach inner@{ el ->
                    if (el.getElementsByAttributeValueContaining("src", wordToScrap)
                            .first()?.attr("src") != null
                    ) {
                        linkImgToDisplay =
                            el.getElementsByAttributeValueContaining("src", wordToScrap)
                                .first()?.attr("src")!!
                        return@outer
                    }
                    println(linkImgToDisplay)
                }
            }
            println(linkImgToDisplay)
            return linkImgToDisplay
        }

        fun getImageAndDisplay(word: String, binding: ImgScraperFragmentBinding) {
            val job = Job()
            val coroutineScope = CoroutineScope(job + Dispatchers.IO)
            if (word.isNotEmpty()) {
                coroutineScope.launch {
                    getImage(word)

                    try {
                        val request =
                            okhttp3.Request.Builder().url("http:$linkImgToDisplay").build()
                        val response = OkHttpClient().newCall(request).execute()
                        val responseToByte = response.body()?.bytes()
                        withContext(Dispatchers.Main) {
                            binding.imgScrap.setImageBitmap(
                                BitmapFactory.decodeByteArray(
                                    responseToByte, 0, responseToByte!!.size
                                )
                            )
                        }
                    } catch (e: java.lang.Exception) {
                        Log.i("Error: ", e.message.toString())
                    }
                }
            } else {
                binding.imgScrap.setImageResource(R.mipmap.error_image_foreground)
            }
        }
    }
}