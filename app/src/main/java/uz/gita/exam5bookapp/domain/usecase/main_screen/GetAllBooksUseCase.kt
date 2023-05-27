package uz.gita.exam5bookapp.domain.usecase.main_screen

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.exam5bookapp.data.model.common.BookData

interface GetAllBooksUseCase {

    suspend operator fun invoke(): Flow<List<BookData>>

    fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>>
}