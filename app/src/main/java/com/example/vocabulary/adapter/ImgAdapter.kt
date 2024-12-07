package com.example.vocabulary.adapter

import android.graphics.BitmapFactory
import android.util.Log
import com.example.vocabulary.R
import com.example.vocabulary.databinding.ImgScraperFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class ImgAdapter {

    companion object {
        var linkImgToDisplay = ""

        fun getImage(wordToScrap: String): String {
            val url = "https://en.wikipedia.org/wiki/$wordToScrap"
            val doc = Jsoup.connect(url).get()
            val scrapingImages: Elements = doc.select("img")
            println("Images:$scrapingImages")

            //run outer@{
            scrapingImages.forEach { el ->
                val src = el.attr("src");
                println("src link: $src")
                if (src.contains(wordToScrap)) {
                    linkImgToDisplay = if (!src.startsWith("https")) "https:$src" else src
                    //el.getElementsByAttributeValueContaining("src", wordToScrap)
                    //     .first()?.attr("src")!!
                    return@forEach
                }
                //}
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
                            okhttp3.Request.Builder().url(linkImgToDisplay).build()
                        println("request: $request")
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