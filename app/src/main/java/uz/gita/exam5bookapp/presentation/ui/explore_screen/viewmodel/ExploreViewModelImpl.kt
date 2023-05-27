package uz.gita.exam5bookapp.presentation.ui.explore_screen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.exam5bookapp.data.model.common.CategoryData
import uz.gita.exam5bookapp.domain.repository.BookRepository
import uz.gita.exam5bookapp.domain.repository.impl.BookRepositoryImpl

/**
 *    Author: Kamolov Samandar
 *    Created: 11.05.2023 at 12:30
 */

class ExploreViewModelImpl: ViewModel(), ExploreViewModel {
    override val liveData = MutableLiveData<List<CategoryData>>()
    private val repository: BookRepository = BookRepositoryImpl.getInstance()

    init {
        getAllBooks()
    }

    override fun getAllBooks() {
        liveData.value = repository.getCatList()
    }
}