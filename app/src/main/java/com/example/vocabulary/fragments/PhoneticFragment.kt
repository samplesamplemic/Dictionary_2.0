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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.phonetic_fragment, container, false)
        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                when (item) {
                    is Resource.Error -> {
                        updateViewVisibility(false)
                    }

                    is Resource.Loading -> {
                        updateViewVisibility(false)
                    }

                    is Resource.Success -> {
                        handleResourceSuccess(item)
                    }
                }
            }
        }
        return binding.root
    }

    override fun handleResourceSuccess(word: Resource<Word>) {
        if (word.data.isNullOrEmpty()) {
            binding.wordSearched.text = word.message
            binding.pronounce.text = ""
        } else {
            updateViewVisibility(true)
            val phoneticToFind = word.data[0].phonetics
            val phoneticFound = PhoneticAdapter.phoneticAdapter(phoneticToFind)
            val wordSearched = word.data[0]
                .word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

            binding.wordSearched.text = wordSearched
            binding.pronounce.text = phoneticFound
            PhoneticAdapter.playPronounce(binding.iconPlay)
        }
    }

    override fun updateViewVisibility(isVisible: Boolean) {
        binding.iconPlay.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}