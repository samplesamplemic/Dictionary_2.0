package com.example.vocabulary.model.dto

data class WordItem(
    val word: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>
)