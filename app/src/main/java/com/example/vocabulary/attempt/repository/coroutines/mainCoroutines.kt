package com.example.vocabulary.attempt.repository.coroutines

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vocabulary.network.WordRetriever
import kotlinx.coroutines.*

class mainCoroutines {

    fun retrieveWord(context: AppCompatActivity, wordToSearch: String) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(context).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val resultList = WordRetriever().getData(wordToSearch)
        }
    }
}