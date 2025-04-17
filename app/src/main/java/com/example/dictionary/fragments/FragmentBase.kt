package com.example.dictionary.fragments

import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.resource.Resource

interface FragmentBase {
    fun handleResourceSuccess(resource: Resource<Word>)
    fun updateViewVisibility(isVisible: Boolean)
}