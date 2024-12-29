import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabulary.R
import com.example.vocabulary.model.dto.Meaning

class MeaningAdapter(private val context: Context, private val meanings: List<Meaning>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PART_OF_SPEECH = 0
        private const val TYPE_DEFINITION = 1
    }

    class PartOfSpeechViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val partOfSpeech: TextView = view.findViewById(R.id.part_of_speech)
    }

    class DefinitionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val definitionText: TextView = view.findViewById(R.id.definition)
        val exampleText: TextView = view.findViewById(R.id.example_text)
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
            TYPE_PART_OF_SPEECH -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_part_of_speech, parent, false)
                PartOfSpeechViewHolder(view)
            }

            TYPE_DEFINITION -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_definition, parent, false)
                DefinitionViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var count = 0
        meanings.forEach { meaning ->
            if (position == count) {
                (holder as PartOfSpeechViewHolder).partOfSpeech.text =
                    meaning.partOfSpeech.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    }
                return
            }
            count++
            meaning.definitions.forEach { definition ->
                if (position == count) {
                    (holder as DefinitionViewHolder).definitionText.text = definition.definition
//                        context.getString(R.string.bullet, definition.definition)

                    if (definition.example != null) {
                        holder.exampleText.text = context.getString(R.string.bullet, "\"${definition.example}\"")
                        holder.exampleText.visibility = View.VISIBLE
                    } else {
                        holder.exampleText.visibility = View.GONE
                    }
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
}
