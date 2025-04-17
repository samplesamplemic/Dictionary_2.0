package com.example.dictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dictionary.R
import com.example.dictionary.databinding.SearchbarFragmentBinding
import com.example.dictionary.handler.setSafeOnClickListener
import com.example.dictionary.viewModel.ItemViewModel

class SearchBarFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var binding: SearchbarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.searchbar_fragment, container, false)

        binding.searchBtn.setSafeOnClickListener {
            val wordToSearch = binding.searchBar.text.toString()
            if (wordToSearch.isNotBlank()) {
                viewModel.selectItem(wordToSearch)
            }
        }
        return binding.root
    }
}

