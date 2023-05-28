package com.example.vocabulary.adapter

import android.util.Log
import com.example.vocabulary.model.dto.Meanings

class DefinitionsAdapter {

    companion object {
        fun definitionAdapter(meanings: List<Meanings>): String {
            val definitionsList: MutableList<String> = mutableListOf()
            var definitions: String = ""
            // val emoticon: String = "\uD83D\uDE05"
            //  definitionsList.add("Searched word doesn't exist, sorry. $emoticon")
            for (element in meanings) {
                val definitionParsed = element.definitions
                for (i in 0 until (definitionParsed.count())) {
                    definitionsList.add(definitionParsed[i].definition)
                    Log.i("Definition: ", definitionsList.toString())
                }
            }
            for (definition in definitionsList) {
                definitions = definitionsList.toString().drop(1).dropLast(1)
                    .replace(Regex("""(\.,|;)"""), ". \n");
            }

            return definitions
        }
    }
}