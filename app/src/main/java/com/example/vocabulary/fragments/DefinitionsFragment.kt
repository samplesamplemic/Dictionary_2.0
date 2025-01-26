package com.example.vocabulary.fragments

import MeaningAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabulary.R
import com.example.vocabulary.databinding.DefinitionsFragmentBinding
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class DefinitionsFragment : Fragment(), FragmentBase {
    private lateinit var binding: DefinitionsFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var meaningAdapter: MeaningAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Error -> {
                        updateViewVisibility(false)
                    }

                    is Resource.Loading -> {
                        updateViewVisibility(false)
                    }

                    is Resource.Success -> {
                        handleResourceSuccess(resource)
                    }
                }
            }
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMeaning.layoutManager = LinearLayoutManager(context)
        recyclerView = binding.root.findViewById(R.id.recyclerViewMeaning)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun handleResourceSuccess(word: Resource<Word>) {
        if (word.data.isNullOrEmpty()) {
            updateViewVisibility(false)
        } else {
            val meanings = word.data[0].meanings
            Log.i("Meanings: ", meanings.toString())
            updateViewVisibility(true)
            meaningAdapter = MeaningAdapter(requireContext(), meanings)
            recyclerView.adapter = meaningAdapter
        }
    }

    override fun updateViewVisibility(isVisible: Boolean) {
        binding.recyclerViewMeaning.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}