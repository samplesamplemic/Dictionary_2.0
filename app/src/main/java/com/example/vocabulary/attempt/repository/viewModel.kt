import android.util.Log
import com.example.vocabulary.adapter.ImgAdapter
import com.example.vocabulary.model.dto.Meaning

// val response: Response<Word> = service.getWord(wordToSearch)
//        return if (response.isSuccessful()) {
//            Log.i("Response body: ", Gson().toJson(response.body()).toString())
//            response.body()
//        } else {
//            Log.e("Error: ", Gson().toJson(response.errorBody()).toString())
//            response.body()
//        }

//}

//-------------------------------------------------------------------

//class ItemViewModel : ViewModel() {
//    private val wordRetriever: WordRetriever = WordRetriever()
//    private val mutableSelectedItem = MutableLiveData<Word>()
//    val selectedItem: LiveData<Word> get() = mutableSelectedItem
//
//    fun selectItem(word: String) {
//        val job = Job()
//        val coroutineScope = CoroutineScope(job + Dispatchers.IO)
//        coroutineScope.launch {
//            mutableSelectedItem.postValue(wordRetriever.getData(word))
//        }
//    }
//}


//            binding.imgScrap.scaleType = ImageView.ScaleType.FIT_XY
//            binding.imgScrap.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//            binding.imgScrap.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
//Toast.makeText(context, "SCALE TYPE - CENTER_CROP", Toast.LENGTH_SHORT).show();

//viewModel.selectedItem.observe(this, Observer { item ->
//    binding.word2.text =
//        if (item.data?.get(0)?.word.isNullOrEmpty()) item.message else item.data?.get(0)?.word
//})

//
//<TextView
//android:id="@+id/word2"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:layout_marginBottom="30dp"
//android:text="@{word.word}"
//android:textColor="@color/white"
//android:textSize="30sp"
//android:textStyle="bold"
//android:typeface="sans"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent" />

//if (el.getElementsByAttributeValueContaining("src", wordToScrap)
//.first()?.attr("src") != null
//)

//imageScraper.kt
//lifecycleScope.launch {
//    viewModel.selectedItem.observe(viewLifecycleOwner) { word ->
//        val wordSearched = word.data?.get(0)?.word
//
//        if (!wordSearched.isNullOrEmpty()) {
//            ImgAdapter.getImageAndDisplay(wordSearched, binding)
//        } else {
//            ImgAdapter.getImageAndDisplay("", binding)
//        }
//    }
//}
//        ("Exit", object : DialogInterface.OnClickListener {
//                override fun onClick(p0: DialogInterface?, p1: Int) {}
//            })

//fun definitionAdapter(meanings: List<Meaning>): String {
//    val definitionsList: MutableList<String> = mutableListOf()
//    var definitions: String = ""
//    // val emoticon: String = "\uD83D\uDE05"
//    //  definitionsList.add("Searched word doesn't exist, sorry. $emoticon")
//    for (element in meanings) {
//        val definitionParsed = element.definitions
//        for (i in 0 until (definitionParsed.count())) {
//            definitionsList.add(definitionParsed[i].definition)
//            Log.i("Definition: ", definitionsList.toString())
//        }
//    }
//    for (definition in definitionsList) {
//        definitions = definitionsList.toString().drop(1).dropLast(1)
//            .replace(Regex("""(\.,|;)"""), ". \n");
//    }
//
//    return definitions
//}
//}