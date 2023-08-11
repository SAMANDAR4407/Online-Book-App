package uz.gita.exam5bookapp.domain.usecase.saved_screen.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.toBookData
import uz.gita.exam5bookapp.domain.repository.BookRepository
import uz.gita.exam5bookapp.domain.repository.impl.BookRepositoryImpl
import uz.gita.exam5bookapp.domain.usecase.saved_screen.SavedBooksUseCase

/**
 *    Created by Kamolov Samandar on 16.05.2023 at 19:56
 */

class SavedBooksUseCaseImpl private constructor(): SavedBooksUseCase{

    companion object {
        private lateinit var instance: SavedBooksUseCase

        fun init() {
            if (!(::instance.isInitialized))
                instance = SavedBooksUseCaseImpl()
        }

        fun getInstance(): SavedBooksUseCase = instance
    }

    private val repository: BookRepository = BookRepositoryImpl.getInstance()

    override fun invoke() = repository.getFavouriteBooksListDB().map {
        it.map { data ->
            data.toBookData()
        }
    }.flowOn(Dispatchers.IO)

    override fun searchBook(query: String): List<BookData> = repository.getBooksByQuery(query).map { it.toBookData() }

}