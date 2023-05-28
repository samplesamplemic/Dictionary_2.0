package com.example.vocabulary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vocabulary.R
import com.example.vocabulary.adapter.PhoneticAdapter
import com.example.vocabulary.databinding.PhoneticFragmentBinding
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class PhoneticFragment : Fragment() {
    private lateinit var searchedWord: TextView
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
                if (item.data.isNullOrEmpty()) {
                    binding.wordSearched.text = item.message
                    binding.pronounce.text = ""
                } else {
                    val phoneticToFind = item.data?.get(0)?.phonetics
                    val phoneticFound = PhoneticAdapter.phoneticAdapter(phoneticToFind)
                    val wordSearched = item.data?.get(0)?.word?.substring(0, 1)
                        ?.uppercase() + item.data?.get(0)?.word?.substring(1)

                    binding.wordSearched.text = wordSearched
                    binding.pronounce.text = phoneticFound
                    PhoneticAdapter.playPronounce(phoneticToFind, binding.iconPlay)
                }


            }
        }
        return binding.root
    }
}