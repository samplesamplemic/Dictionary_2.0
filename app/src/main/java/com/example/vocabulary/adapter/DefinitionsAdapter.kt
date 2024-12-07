package com.example.vocabulary.adapter

import com.example.vocabulary.model.dto.Meaning

class DefinitionsAdapter {

    companion object {
        fun definitionAdapter(meanings: List<Meaning>): String {
            val definitionsList = meanings.flatMap { it ->
                it.definitions.map {
                    it.definition
                }
            }
            return definitionsList.joinToString("\n") { it.replace(Regex("""(\.,|;)"""), ".") }
        }
    }
}