package uz.gita.exam5bookapp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val genre: String = "",
    val page: Int = 0,
    val rate: Int = 0,
    val year: String = "",
    val bookUrl: String = "",
    val coverUrl: String = "",
    val reference: String = "",

    val lastPage: Int = 0,
    var saved: Boolean = false
): Serializable