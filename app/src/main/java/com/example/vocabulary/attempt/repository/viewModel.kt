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