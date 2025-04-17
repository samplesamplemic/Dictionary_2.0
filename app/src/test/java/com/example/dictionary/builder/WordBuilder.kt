package com.example.dictionary.builder

import com.example.dictionary.model.dto.Definition
import com.example.dictionary.model.dto.Meaning
import com.example.dictionary.model.dto.Phonetic
import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.dto.WordItem

class WordBuilder {
    private val wordItems = mutableListOf<WordItem>()

    fun withWordItem(wordItem: WordItem): WordBuilder {
        wordItems.add(wordItem)
        return this
    }

    fun build(): Word {
        return Word().apply {
            addAll(wordItems)
        }
    }

    companion object {
        fun defaultWord(): Word {
            return WordBuilder()
                .withWordItem(
                    WordItem(
                        word = "example",
                        phonetics = listOf(
                            Phonetic(
                                text = "/ɪɡˈzæmpl/",
                                audio = "https://www.example.com/audio.mp3"
                            )
                        ),
                        meanings = listOf(
                            Meaning(
                                partOfSpeech = "noun",
                                definitions = listOf(
                                    Definition(
                                        definition = "A thing characteristic of its kind or illustrating a general rule.",
                                        example = "It's a good example of how European action can produce results."
                                    ),
                                    Definition(
                                        definition = "A person or thing regarded in terms of their fitness to be imitated or the likelihood of their being imitated.",
                                        example = "He followed his sister's example and deserted his family."
                                    )
                                )
                            )
                        )
                    )
                ).build()
        }
    }
}