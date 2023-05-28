package com.example.vocabulary.imgScraper

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vocabulary.R
import com.example.vocabulary.adapter.ImgAdapter
import com.example.vocabulary.databinding.ImgScraperFragmentBinding
import com.example.vocabulary.databinding.PopupImageBinding
import com.example.vocabulary.viewModel.ItemViewModel
import kotlinx.coroutines.launch


class ImgScraper : Fragment() {
    private lateinit var binding: ImgScraperFragmentBinding
    private val viewModel: ItemViewModel by activityViewModels()

    var linkImgToDisplay = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.img_scraper_fragment, container, false)

        lifecycleScope.launch {
            viewModel.selectedItem.observe(viewLifecycleOwner) { word ->
                val wordSearched = word.data?.get(0)?.word

                if (!wordSearched.isNullOrEmpty()) {
                    ImgAdapter.getImageAndDisplay(wordSearched, binding)
                } else {
                    ImgAdapter.getImageAndDisplay("", binding)
                }
            }
        }
        binding.imgScrap.setOnClickListener {
             alertImage()
        }
        return binding.root
    }

    private fun alertImage() {
        lateinit var alertImage: AlertDialog
        val alertBinding = PopupImageBinding.inflate(LayoutInflater.from(requireContext()))

        val builder = AlertDialog.Builder(requireContext())
        val imgPopup = alertBinding.imgPopup
        alertImage = builder.setView(alertBinding.root)
            .setCancelable(false)
            .setPositiveButton("Exit", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {}
            })
            .create()

        imgPopup.setImageDrawable(binding.imgScrap.drawable)
        alertImage.show()
    }
}