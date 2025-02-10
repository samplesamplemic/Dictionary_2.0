package com.example.vocabulary.fragments

import MeaningAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabulary.databinding.DefinitionsFragmentBinding
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class DefinitionsFragment : Fragment(), FragmentBase {
    private var _binding: DefinitionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var meaningAdapter: MeaningAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DefinitionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Error -> updateViewVisibility(false)
                    is Resource.Loading -> updateViewVisibility(false)
                    is Resource.Success -> handleResourceSuccess(resource)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMeaning.layoutManager = LinearLayoutManager(requireContext())
        meaningAdapter = MeaningAdapter(emptyList())
        binding.recyclerViewMeaning.adapter = meaningAdapter
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