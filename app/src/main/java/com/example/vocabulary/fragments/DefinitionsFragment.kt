package com.example.vocabulary.fragments

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
import com.example.vocabulary.R
import com.example.vocabulary.adapter.MeaningAdapter
import com.example.vocabulary.databinding.MeaningFragmentBinding
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch

class DefinitionsFragment : Fragment() {
    private lateinit var binding: MeaningFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var meaningAdapter: MeaningAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.meaning_fragment, container, false)

        meaningAdapter = MeaningAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = meaningAdapter

        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
                if (item.data.isNullOrEmpty()) {
                    //binding.definitionText.text = item.message
                } else {
                    val meanings = item.data[0].meanings
                    Log.i("Meanings: ", meanings.toString())
                    // binding.definitionText.text = DefinitionsAdapter.definitionAdapter(meanings)
                    //binding.partOfSpeech.text = meanings[0].partOfSpeech
                    meaningAdapter.updateData(meanings)
                }
            }
        }
        return binding.root
    }
}