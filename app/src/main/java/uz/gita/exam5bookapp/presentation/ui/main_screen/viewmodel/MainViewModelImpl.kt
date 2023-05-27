package uz.gita.exam5bookapp.presentation.ui.main_screen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.toBookData
import uz.gita.exam5bookapp.domain.repository.BookRepository
import uz.gita.exam5bookapp.domain.repository.impl.BookRepositoryImpl
import uz.gita.exam5bookapp.domain.usecase.main_screen.GetAllBooksUseCase
import uz.gita.exam5bookapp.domain.usecase.main_screen.impl.GetAllBooksUseCaseImpl

class MainViewModelImpl: ViewModel(), MainViewModel {
    private val useCase = GetAllBooksUseCaseImpl.getInstance()
    override val liveData = MutableLiveData<List<BookData>>()

    init {
        viewModelScope.launch {
            getClassicBooks()
        }
    }

    override suspend fun getClassicBooks() {
        useCase.invoke().onEach {
            liveData.value = it
        }.launchIn(viewModelScope)
    }


}