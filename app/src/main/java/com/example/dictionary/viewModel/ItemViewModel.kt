package com.example.dictionary.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.resource.Resource
import com.example.dictionary.network.WordRetriever
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val wordRetriever: WordRetriever
) : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Resource<Word>>()
    val selectedItem: LiveData<Resource<Word>> get() = mutableSelectedItem

    fun selectItem(word: String) = viewModelScope.launch {
        mutableSelectedItem.postValue(Resource.Loading())
        try {
            val result = wordRetriever.getData(word)
            mutableSelectedItem.postValue(result)
        } catch (e: Exception) {
            mutableSelectedItem.postValue(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}


