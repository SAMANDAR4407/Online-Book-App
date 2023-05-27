package uz.gita.exam5bookapp.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.common.CategoryData
import uz.gita.exam5bookapp.data.model.response.BookResponse
import uz.gita.exam5bookapp.data.source.local.room.entity.BookEntity


interface BookRepository {

    suspend fun getBooksList(): Flow<List<BookResponse>>

    suspend fun getBooksListDB(): Flow<List<BookResponse>>

    suspend fun getFavouriteBooksListDB(): Flow<List<BookResponse>>

    suspend fun getBookListSize(): Int

    suspend fun loadBook(book: BookResponse): Flow<Boolean>

    suspend fun getLastPage(id: Int): Int

    suspend fun setLastPage(book: BookEntity)

    suspend fun isBookFavouriteDB(book: BookData): Flow<Boolean>

    fun loadBooks()

    fun getCatList(): List<CategoryData>

    fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>>
}