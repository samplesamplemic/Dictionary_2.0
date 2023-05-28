package com.example.vocabulary.model.dto

class Word : ArrayList<WordItem>()

data class WordItem(
    val word: String,
    val phonetics: List<Phonetics>,
    val meanings: List<Meanings>
)

data class Phonetics(
    val text: String?,
    val audio: String?
)

data class Meanings(
    val partOfSpeech: String,
    val definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val example: String
)
