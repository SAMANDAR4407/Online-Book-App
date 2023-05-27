package uz.gita.exam5bookapp.data.source.local.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.exam5bookapp.data.source.local.room.entity.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * from books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * from books where saved = 1")
    fun getAllFavBooks(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<BookEntity>)

    @Query("DELETE from books")
    fun deleteBooks()

    @Query("SELECT * from books where id = :id")
    fun getBook(id: Int): BookEntity

    @Update
    fun updateBook(book: BookEntity)

    @Query("UPDATE BOOKS SET lastPage = :lastPage where id = :bookId")
    fun updateBook(lastPage: Int, bookId: Int)
}