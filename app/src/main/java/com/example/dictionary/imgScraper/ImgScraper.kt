package com.example.dictionary.imgScraper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.adapter.ImgAdapter
import com.example.dictionary.databinding.ImgScraperFragmentBinding
import com.example.dictionary.databinding.PopupImageBinding
import com.example.dictionary.viewModel.ItemViewModel
import kotlinx.coroutines.launch


class ImgScraper : Fragment() {
    private var binding: ImgScraperFragmentBinding? = null
    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.img_scraper_fragment, container, false)
        setupObservers()
        binding?.imgScrap?.setOnClickListener {
            alertImage()
        }
        return binding?.root
    }

    private fun setupObservers() {
        viewModel.selectedItem.observe(viewLifecycleOwner) { word ->
            val wordSearched = word.data?.get(0)?.word
            lifecycleScope.launch {
                if (!wordSearched.isNullOrEmpty()) {
                    ImgAdapter.getImageAndDisplay(wordSearched, binding!!)
                } else {
                    ImgAdapter.getImageAndDisplay("", binding!!)
                }
            }
        }
    }

    private fun alertImage() {
        val alertBinding = PopupImageBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        val imgPopup = alertBinding.imgPopup
        val alertImage = builder.setView(alertBinding.root)
            .setCancelable(false)
            .setPositiveButton("Exit") { dialog, _ -> dialog.dismiss() }
            .create()

        imgPopup.setImageDrawable(binding?.imgScrap?.drawable)
        alertImage.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}