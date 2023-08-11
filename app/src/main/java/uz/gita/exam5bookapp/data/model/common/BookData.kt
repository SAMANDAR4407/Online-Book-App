package uz.gita.exam5bookapp.data.model.common

import java.io.Serializable

data class BookData(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val genre: String = "",
    val about: String = "",
    val page: Int = 0,
    val rate: Int = 0,
    val year: String = "",
    val bookUrl: String = "",
    val coverUrl: String = "",
    val reference: String = "",

    val lastPage: Int = 0,
    var saved: Boolean = false
): Serializable