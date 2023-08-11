package uz.gita.exam5bookapp.presentation.ui.search_screen.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.response.BookResponse

/**
 *    Created by Kamolov Samandar on 05.07.2023 at 16:09
 */

interface SearchViewModel {

    val searchResults : LiveData<List<BookData>>

    fun searchBooks(query: String)
}