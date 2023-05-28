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
import com.example.vocabulary.adapter.DefinitionsAdapter
import com.example.vocabulary.databinding.DefinitionsFragmentBinding
import com.example.vocabulary.model.dto.Meanings
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class DefinitionsFragment : Fragment() {
    private lateinit var binding: DefinitionsFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var meanings: List<Meanings>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.definitions_fragment, container, false)
        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                if (item.data.isNullOrEmpty()) {
                    binding.definitionId.text = item.message
                } else {
                    meanings = item.data[0].meanings
                    binding.definitionId.text = DefinitionsAdapter.definitionAdapter(meanings)
                }
            }
        }
        return binding.root
    }
}