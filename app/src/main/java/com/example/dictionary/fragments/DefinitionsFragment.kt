package com.example.dictionary.fragments

import MeaningAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.DefinitionsFragmentBinding
import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.resource.Resource
import com.example.dictionary.viewModel.ItemViewModel

class DefinitionsFragment : Fragment(), FragmentBase {
    private var _binding: DefinitionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by activityViewModels()
    private val meaningAdapter by lazy { MeaningAdapter(emptyList()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DefinitionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.selectedItem.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error, is Resource.Loading -> updateViewVisibility(false)
                is Resource.Success -> handleResourceSuccess(resource)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMeaning.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = meaningAdapter
        }
    }

    override fun handleResourceSuccess(resource: Resource<Word>) {
        resource.data?.let { word ->
            val meanings = word[0].meanings
            updateViewVisibility(meanings.isNotEmpty())
            meaningAdapter.updateData(meanings)
        } ?: updateViewVisibility(false)
    }

    override fun updateViewVisibility(isVisible: Boolean) {
        binding.recyclerViewMeaning.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}