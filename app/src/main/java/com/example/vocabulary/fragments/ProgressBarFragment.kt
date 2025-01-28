package com.example.vocabulary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.vocabulary.R
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.viewModel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgressBarFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.progress_bar_fragment, container, false)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Error -> progressBar.visibility = View.GONE
                is Resource.Loading -> progressBar.visibility = View.VISIBLE
                is Resource.Success -> progressBar.visibility = View.GONE
            }
        })
        return view
    }
}