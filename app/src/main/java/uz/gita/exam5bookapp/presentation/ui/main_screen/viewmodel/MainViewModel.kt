package uz.gita.exam5bookapp.presentation.ui.main_screen.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.exam5bookapp.data.model.common.BookData

interface MainViewModel {
    val liveData: LiveData<List<BookData>>

    suspend fun getClassicBooks()
//    suspend fun getLastReadBook()
}