package com.example.vocabulary

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.vocabulary.databinding.ActivityMainBinding
import com.example.vocabulary.viewModel.ItemViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        viewModel.loadingState.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }


}
