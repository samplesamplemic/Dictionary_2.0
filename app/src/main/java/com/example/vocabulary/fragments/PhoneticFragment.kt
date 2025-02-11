package com.example.vocabulary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vocabulary.R
import com.example.vocabulary.adapter.PhoneticAdapter
import com.example.vocabulary.databinding.PhoneticFragmentBinding
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class PhoneticFragment : Fragment(), FragmentBase {
    private lateinit var binding: PhoneticFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()
    private val phoneticAdapter = PhoneticAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.phonetic_fragment, container, false)
        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                when (item) {
                    is Resource.Error -> updateViewVisibility(false)
                    is Resource.Loading -> updateViewVisibility(false)
                    is Resource.Success -> handleResourceSuccess(item)
                }
            }
        }
        return binding.root
    }

    override fun handleResourceSuccess(resource: Resource<Word>) {
        val wordList = resource.data
        if (wordList.isNullOrEmpty()) {
            binding.wordSearched.text = resource.message ?: ""
            binding.pronounce.text = ""
            updateViewVisibility(false)
        } else {
            updateViewVisibility(true)
            val wordItem = wordList[0]
            val (phoneticText, phoneticAudio) = phoneticAdapter.getPhoneticText(wordItem.phonetics)

            binding.wordSearched.text = wordItem.word.replaceFirstChar { it.titlecase(Locale.ROOT) }
            binding.pronounce.text = phoneticText
            phoneticAdapter.playPronounce(binding.iconPlay, phoneticAudio)
        }
    }

    override fun updateViewVisibility(isVisible: Boolean) {
        binding.iconPlay.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.wordSearched.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.pronounce.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}