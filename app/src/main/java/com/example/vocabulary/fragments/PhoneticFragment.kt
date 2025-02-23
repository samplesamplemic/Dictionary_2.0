package com.example.vocabulary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vocabulary.R
import com.example.vocabulary.adapter.PhoneticAdapter
import com.example.vocabulary.databinding.PhoneticFragmentBinding
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import java.util.Locale

class PhoneticFragment : Fragment(), FragmentBase {
    private var _binding: PhoneticFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by activityViewModels()
    private val phoneticAdapter = PhoneticAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.phonetic_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedItem.observe(viewLifecycleOwner) { word ->
            when (word) {
                is Resource.Error, is Resource.Loading -> updateViewVisibility(false)
                is Resource.Success -> handleResourceSuccess(word)
            }
        }
    }

    override fun handleResourceSuccess(resource: Resource<Word>) {
        val wordItem = resource.data?.firstOrNull()

        if (wordItem == null) {
            binding.apply {
                wordSearched.text = resource.message ?: ""
                pronounce.text = ""
            }
            updateViewVisibility(false)
            return
        }

        updateViewVisibility(true)
        val (phoneticText, phoneticAudio) = phoneticAdapter.getPhoneticText(wordItem.phonetics)
        binding.apply {
            wordSearched.text = wordItem.word.replaceFirstChar { it.titlecase(Locale.ROOT) }
            pronounce.text = phoneticText
        }
        phoneticAdapter.playPronounce(binding.iconPlay, phoneticAudio)
    }

    override fun updateViewVisibility(isVisible: Boolean) {
        binding.apply {
            iconPlay.isVisible = isVisible
            wordSearched.isVisible = isVisible
            pronounce.isVisible = isVisible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}