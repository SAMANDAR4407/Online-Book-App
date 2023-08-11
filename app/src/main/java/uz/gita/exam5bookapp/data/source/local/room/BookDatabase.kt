package uz.gita.exam5bookapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.exam5bookapp.data.source.local.room.entity.BookEntity
import uz.gita.exam5bookapp.data.source.local.room.dao.BookDao

@Database(entities = [BookEntity::class], version = 3)
abstract class BookDatabase: RoomDatabase() {
    abstract fun getDao(): BookDao

    companion object{
        private lateinit var instance: BookDatabase

        fun init(context: Context) {
            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(context, BookDatabase::class.java, "books.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

        fun getInstance() = instance
    }
}