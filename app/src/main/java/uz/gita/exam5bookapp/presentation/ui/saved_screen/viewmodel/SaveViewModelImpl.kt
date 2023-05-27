package uz.gita.exam5bookapp.presentation.ui.saved_screen.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.domain.usecase.saved_screen.impl.SavedBooksUseCaseImpl

/**
 *    Created by Kamolov Samandar on 12.05.2023 at 18:44
 */

class SaveViewModelImpl : SavedViewModel, ViewModel() {
    private val useCase = SavedBooksUseCaseImpl.getInstance()
    override val savedLiveData = MutableLiveData<List<BookData>>()

    init {
        loadBooks()
    }

    override fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase().collect{
                savedLiveData.postValue(it)
                Log.d("AAA", "loadBooks: called ${it.size}")
            }
        }
    }

}