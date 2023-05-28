package com.example.vocabulary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vocabulary.R
import com.example.vocabulary.databinding.SearchbarFragmentBinding
import com.example.vocabulary.handler.SafeClickListener
import com.example.vocabulary.viewModel.ItemViewModel

class SearchBarFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var binding: SearchbarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.searchbar_fragment, container, false)

        binding.searchBtn.setSafeOnClickListener {
            val wordToSearch = binding.searchBar.text.toString()
            viewModel.selectItem(wordToSearch)
        }
        return binding.root
    }

    private fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
}

