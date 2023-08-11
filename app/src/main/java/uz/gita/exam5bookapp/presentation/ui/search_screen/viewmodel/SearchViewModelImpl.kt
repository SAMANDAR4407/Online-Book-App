package uz.gita.exam5bookapp.presentation.ui.search_screen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.domain.usecase.saved_screen.impl.SavedBooksUseCaseImpl

/**
 *    Created by Kamolov Samandar on 05.07.2023 at 16:10
 */

class SearchViewModelImpl: SearchViewModel, ViewModel() {

    private val useCase = SavedBooksUseCaseImpl.getInstance()
    override val searchResults = MutableLiveData<List<BookData>>()

    override fun searchBooks(query: String) {
        searchResults.value = useCase.searchBook(query)
    }
}