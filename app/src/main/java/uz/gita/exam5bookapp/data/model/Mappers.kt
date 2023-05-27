package uz.gita.exam5bookapp.data.model

import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.request.BookRequest
import uz.gita.exam5bookapp.data.model.response.BookResponse
import uz.gita.exam5bookapp.data.source.local.room.entity.BookEntity

fun BookResponse.toBookEntity() = BookEntity(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookResponse.toBookData() = BookData(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookResponse.toBookRequest() = BookRequest(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)

fun BookRequest.toBookEntity() = BookEntity(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookRequest.toBookData() = BookData(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookRequest.toBookResponse() = BookResponse(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)


fun BookData.toBookEntity() = BookEntity(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference, lastPage, saved)
fun BookData.toBookResponse() = BookResponse(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookData.toBookRequest() = BookRequest(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)


fun BookEntity.toBookData() = BookData(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference, lastPage, saved)
fun BookEntity.toBookResponse() = BookResponse(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)
fun BookEntity.toBookRequest() = BookRequest(id, title, author, genre, page, rate, year, bookUrl, coverUrl, reference)


