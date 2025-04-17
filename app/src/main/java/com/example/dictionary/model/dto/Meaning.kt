package com.example.dictionary.model.dto

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)