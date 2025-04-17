import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemDefinitionBinding
import com.example.dictionary.databinding.ItemPartOfSpeechBinding
import com.example.dictionary.model.dto.Meaning

class MeaningAdapter(private var meanings: List<Meaning>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PART_OF_SPEECH = 0
        private const val TYPE_DEFINITION = 1
    }

    class PartOfSpeechViewHolder(private val binding: ItemPartOfSpeechBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.partOfSpeech.text = meaning.partOfSpeech.replaceFirstChar { it.uppercase() }
        }
    }

    class DefinitionViewHolder(private val binding: ItemDefinitionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(definition: String, example: String?) {
            binding.definition.text = definition
            if (!example.isNullOrEmpty()) {
                binding.exampleText.text = "• \"$example\""
                binding.exampleText.visibility = View.VISIBLE
            } else {
                binding.exampleText.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isPartOfSpeech(position) -> TYPE_PART_OF_SPEECH
            else -> TYPE_DEFINITION
        }
    }

    private fun isPartOfSpeech(position: Int): Boolean {
        var count = 0
        meanings.forEach { meaning ->
            if (position == count) return true
            count += meaning.definitions.size + 1
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PART_OF_SPEECH -> PartOfSpeechViewHolder(
                ItemPartOfSpeechBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            TYPE_DEFINITION -> DefinitionViewHolder(
                ItemDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var count = 0
        meanings.forEach { meaning ->
            if (position == count) {
                (holder as PartOfSpeechViewHolder).bind(meaning)
                return
            }
            count++
            meaning.definitions.forEach { definition ->
                if (position == count) {
                    (holder as DefinitionViewHolder).bind(definition.definition, definition.example)
                    return
                }
                count++
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        meanings.forEach { meaning ->
            count++
            count += meaning.definitions.size
        }
        return count
    }

    fun updateData(newMeanings: List<Meaning>) {
        meanings = newMeanings
        notifyDataSetChanged()
    }
}
