package uz.gita.exam5bookapp.presentation.ui.description_screen.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.domain.usecase.main_screen.GetAllBooksUseCase
import uz.gita.exam5bookapp.domain.usecase.main_screen.impl.GetAllBooksUseCaseImpl

/**
 *    Created by Kamolov Samandar on 14.05.2023 at 5:20
 */

class DescriptionViewModelImpl : DescriptionViewModel, ViewModel() {
    override val fileDownloadedLiveData = MutableLiveData<BookData>()
    override val errorDownloadLiveData = MutableLiveData<String>()
    override val progressLiveData =MutableLiveData<Boolean>()
    private val useCase: GetAllBooksUseCase = GetAllBooksUseCaseImpl.getInstance()

    override fun downloadBook(context: Context, data: BookData) {
        progressLiveData.value = true
        useCase.downloadBook(context, data)
            .onEach { result ->
                result.onSuccess {
                    progressLiveData.value = false
                    fileDownloadedLiveData.value = it
                }
                result.onFailure {
                    progressLiveData.value = false
                    errorDownloadLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
    }
}