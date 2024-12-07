package com.example.vocabulary.model.dto

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)