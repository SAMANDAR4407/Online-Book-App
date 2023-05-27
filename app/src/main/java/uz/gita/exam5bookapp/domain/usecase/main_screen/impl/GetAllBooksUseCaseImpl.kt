package uz.gita.exam5bookapp.domain.usecase.main_screen.impl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.toBookData
import uz.gita.exam5bookapp.domain.repository.BookRepository
import uz.gita.exam5bookapp.domain.repository.impl.BookRepositoryImpl
import uz.gita.exam5bookapp.domain.usecase.main_screen.GetAllBooksUseCase

class GetAllBooksUseCaseImpl private constructor(): GetAllBooksUseCase {
    companion object {
        private lateinit var instance: GetAllBooksUseCase

        fun init() {
            if (!(::instance.isInitialized))
                instance = GetAllBooksUseCaseImpl()
        }

        fun getInstance(): GetAllBooksUseCase = instance
    }

    private val repository: BookRepository = BookRepositoryImpl.getInstance()

    override suspend operator fun invoke() = flow {
        repository.getBooksList().collect{ doc ->
            emit(doc.map { it.toBookData() })   /** repositorydan BookResponse` tipda olib BookData tipga o`tkazib olib viewModelga jo`natadi */
        }
    }

    override fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>> =
        repository.downloadBook(context, data)


}