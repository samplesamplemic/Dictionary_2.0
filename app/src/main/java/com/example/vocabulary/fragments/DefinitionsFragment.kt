package com.example.vocabulary.fragments

import MeaningAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabulary.R
import com.example.vocabulary.databinding.DefinitionsFragmentBinding
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class DefinitionsFragment : Fragment() {
    private lateinit var binding: DefinitionsFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var meaningAdapter: MeaningAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.definitions_fragment, container, false)
        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recyclerViewMeaning)
        recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                if (item.data.isNullOrEmpty()) {
                    //binding.definitionText.text = item.message
                } else {
                    val meanings = item.data[0].meanings
                    Log.i("Meanings: ", meanings.toString())
                    meaningAdapter = MeaningAdapter(requireContext(), meanings)
                    recyclerView.adapter = meaningAdapter
                }
            }
        }
        return binding.root
    }
}