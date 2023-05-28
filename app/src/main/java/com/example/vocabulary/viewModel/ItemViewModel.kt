package com.example.vocabulary.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.network.WordRetriever
import kotlinx.coroutines.launch

class ItemViewModel() : ViewModel() {

    private val wordRetriever: WordRetriever = WordRetriever()
    private val mutableSelectedItem = MutableLiveData<Resource<Word>>()
    val selectedItem: LiveData<Resource<Word>> get() = mutableSelectedItem


    fun selectItem(word: String) = viewModelScope.launch {
        mutableSelectedItem.postValue(wordRetriever.getData(word))
    }
}


