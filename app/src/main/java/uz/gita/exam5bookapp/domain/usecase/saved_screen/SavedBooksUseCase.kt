package uz.gita.exam5bookapp.domain.usecase.saved_screen

import kotlinx.coroutines.flow.Flow
import uz.gita.exam5bookapp.data.model.common.BookData

/**
 *    Created by Kamolov Samandar on 16.05.2023 at 19:49
 */
interface SavedBooksUseCase {

    operator fun invoke(): Flow<List<BookData>>
}