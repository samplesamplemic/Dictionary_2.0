package com.example.vocabulary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabulary.R
import com.example.vocabulary.model.dto.Meaning

class MeaningAdapter(private var meanings: List<Meaning>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PART_OF_SPEECH = 0
        private const val TYPE_DEFINITION = 1
        private const val TYPE_EXAMPLE = 2
    }

    class PartOfSpeechViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partOfSpeech: TextView = itemView.findViewById(R.id.part_of_speech)
    }

    class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val definitionText: TextView = itemView.findViewById(R.id.definition)
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exampleText: TextView = itemView.findViewById(R.id.example)
    }

    override fun getItemViewType(position: Int): Int {
        val itemIndex = getItemIndex(position)
        return when {
            itemIndex.second == 0 -> TYPE_PART_OF_SPEECH
            itemIndex.second == 1 -> TYPE_DEFINITION
            else -> TYPE_EXAMPLE
        }
    }

    private fun getItemIndex(position: Int): Triple<Int, Int, Int> {
        var pos = position
        for (i in meanings.indices) {
            val definitionCount = meanings[i].definitions.size
            if (pos == 0) return Triple(i, 0, 0)
            pos -= 1
            for (j in 0 until definitionCount) {
                if (pos == 0) return Triple(i, 1, j)
                pos -= 1
                if (meanings[i].definitions[j].example != null && pos == 0) return Triple(i, 2, j)
                pos -= 1
            }
        }
        throw IndexOutOfBoundsException("Invalid position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PART_OF_SPEECH -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_part_of_speech,
                    parent,
                    false
                )
                PartOfSpeechViewHolder(view)
            }

            TYPE_DEFINITION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_definition, parent, false)
                DefinitionViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_example, parent, false)
                ExampleViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemIndex = getItemIndex(position)
        val meaning = meanings[itemIndex.first]
        val definition = meaning.definitions[itemIndex.third]
        when (holder) {
            is PartOfSpeechViewHolder -> holder.partOfSpeech.text = meaning.partOfSpeech
            is DefinitionViewHolder -> holder.definitionText.text = definition.definition
            is ExampleViewHolder -> holder.exampleText.text = definition.example
        }
    }

    override fun getItemCount() = meanings.sumBy { it.definitions.size * 2 + 1 }
    fun updateData(newMeanings: List<Meaning>) {
        meanings = newMeanings
        notifyDataSetChanged()
    }
}
