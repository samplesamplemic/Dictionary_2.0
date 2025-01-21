package com.example.vocabulary.fragments

import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource

interface FragmentBase {
    fun handleResourceSuccess(word: Resource<Word>)
    fun updateViewVisibility(isVisible: Boolean)
}