package uz.gita.exam5bookapp.data.model.request

import java.io.Serializable

data class BookRequest(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val genre: String = "",
    val page: Int = 0,
    val rate: Int = 0,
    val year: String = "",
    val bookUrl: String = "",
    val coverUrl: String = "",
    val reference: String = ""
): Serializable