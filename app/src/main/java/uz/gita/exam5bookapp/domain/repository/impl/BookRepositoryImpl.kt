package uz.gita.exam5bookapp.domain.repository.impl

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.model.common.CategoryData
import uz.gita.exam5bookapp.data.model.response.BookResponse
import uz.gita.exam5bookapp.data.model.toBookEntity
import uz.gita.exam5bookapp.data.model.toBookResponse
import uz.gita.exam5bookapp.data.source.local.room.BookDatabase
import uz.gita.exam5bookapp.data.source.local.room.dao.BookDao
import uz.gita.exam5bookapp.data.source.local.room.entity.BookEntity
import uz.gita.exam5bookapp.domain.repository.BookRepository
import java.io.File
import java.io.FileOutputStream

class BookRepositoryImpl private constructor(private var context: Context) : BookRepository {
    companion object {
        private lateinit var instance: BookRepository

        fun init(context: Context) {
            if (!(::instance.isInitialized))
                instance = BookRepositoryImpl(context)

        }

        fun getInstance(): BookRepository = instance
    }

    private var dao: BookDao = BookDatabase.getInstance().getDao()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var booksRef: CollectionReference = FirebaseFirestore.getInstance().collection("books")

    private val TAG = "BookRepositoryImpl"
    private var _bookListSize = 0
    private val catList = ArrayList<CategoryData>()

    init {
        loadBooks()
    }

    override fun getBooksList(): Flow<List<BookResponse>> = callbackFlow<List<BookResponse>> {
        val allList = ArrayList<BookEntity>()
        booksRef.get().addOnSuccessListener {
            // category list
            it.onEach { document ->
                // category (only one)
                Log.d(TAG, "getBooksList: id: ${document.id}")
                booksRef.document(document.id).collection("all books").get()
                    .addOnSuccessListener {
                        // all books (only one) list bor
                        it.onEach { doc ->
                            // list item
                            val book = doc.toObject(BookResponse::class.java).toBookEntity()
                            if (File(book.reference).exists()) {
                                book.saved = true
                            }
//                            Log.d(TAG, "getBooksList: $doc")
                            allList.add(book)
                        }
                        dao.deleteBooks()
                        dao.insertBooks(allList)
                        val list = dao.getAllBooks().map { it.toBookResponse() }
                        trySendBlocking(list)
                        Log.d(TAG, "getBooksList: size ${list.size}")
                    }
            }
        }
            .addOnFailureListener {
                Log.d(TAG, "getBooksList: list get error, " + it.message.toString())
                // failed
            }
        awaitClose {}
    }.flowOn(Dispatchers.IO)

    override fun getBooksListDB(): Flow<List<BookResponse>> = flow {
        emit(dao.getAllBooks().map { it.toBookResponse() })
    }.flowOn(Dispatchers.IO)

    override fun getFavouriteBooksListDB(): Flow<List<BookResponse>> = dao.getAllFavBooks().map {
        it.map { data ->
            data.toBookResponse()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getBookListSize(): Int = _bookListSize

    override fun getBooksByQuery(query: String): List<BookResponse> {
        val list = arrayListOf<BookResponse>()
        dao.searchBook(query).forEach {
            list.add(it.toBookResponse())
        }
        return list
    }


    override fun loadBook(book: BookResponse): Flow<Boolean> = callbackFlow {
        val gsReference = storage.getReferenceFromUrl(book.bookUrl)

        val ONE_MEGABYTE: Long = 20 * 1024 * 1024 // 20 mb limit
        gsReference.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { bytes ->
                Log.d(TAG, "loadBook: success, fileSize " + bytes.size.toString())
//                trySendBlocking(LoadBookByteData(book.toBookResponse(), bytes))

                val isSaved = runBlocking(Dispatchers.IO) { // bu coroutine contextga tashlandi va return qiymati runBlocking olib chiqib berayabdi
                    return@runBlocking saveBookToFolder(context, book.title, bytes) // boolean qaytaradi
                }
                trySendBlocking(isSaved)
            }
            .addOnFailureListener {
                Log.d(TAG, "loadBook: failure, " + it.message.toString())
//                trySendBlocking()
            }
        awaitClose { }
    }.flowOn(Dispatchers.IO)

    private suspend fun saveBookToFolder(context: Context, fileTitle: String, bytes: ByteArray): Boolean {
        val fileName = fileTitle.replace(" ", "_")

        try {
            val dir = File(context.filesDir, "books")
            if (!dir.exists()) {
                dir.mkdir()  // creates if not exist
            }
            Log.d(TAG, "saveBookToFolder saved: ${dir}/${fileName}.pdf")


            // internal storage + "/" + book.fileName + ".pdf"
            val out = withContext(Dispatchers.IO) {
                FileOutputStream("${dir}/${fileName}.pdf")
            }
            withContext(Dispatchers.IO) {
                out.write(bytes)
            }
            withContext(Dispatchers.IO) {
                out.close()
            }
            return true
            // incrementDownloadCount() /////////***///

        } catch (e: Throwable) {
            Log.d(TAG, "saveBookToFolder error: " + e.message.toString())
            return false
//            Toast.makeText(requireContext(), "Saving book to folder failed: " + e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun isBookFavouriteDB(book: BookData): Flow<Boolean> = flow {
        dao.updateBook(book.toBookEntity())
        Log.d(TAG, "book Saved is changed to:" + book.saved)
        emit(true)
    }.catch {
        //
    }.flowOn(Dispatchers.IO)

    override suspend fun getLastPage(id: Int): Int = dao.getBook(id).lastPage

    override suspend fun setLastPage(book: BookEntity) = dao.updateBook(book)

    override fun loadBooks() {
        val classic = ArrayList<BookData>()
        val fantasy = ArrayList<BookData>()
        val thriller = ArrayList<BookData>()
        val psycho = ArrayList<BookData>()

        booksRef.document("Thriller").collection("all books")
            .get().addOnSuccessListener { doc ->
                doc.map {
                    thriller.add(it.toObject(BookData::class.java))
                }
            }
        booksRef.document("Fantasy").collection("all books")
            .get().addOnSuccessListener { doc ->
                doc.map {
                    fantasy.add(it.toObject(BookData::class.java))
                }
            }
        booksRef.document("Psychology").collection("all books")
            .get().addOnSuccessListener { doc ->
                doc.map {
                    psycho.add(it.toObject(BookData::class.java))
                }
            }
        booksRef.document("Classic Literature").collection("all books")
            .get().addOnSuccessListener { doc ->
                doc.map {
                    classic.add(it.toObject(BookData::class.java))
                }
            }

        catList.add(CategoryData("Fantasy", fantasy))
        catList.add(CategoryData("Thriller", thriller))
        catList.add(CategoryData("Classic Literature", classic))
        catList.add(CategoryData("Psychology", psycho))
    }

    override fun getCatList() = catList

    override fun downloadBook(context: Context, data: BookData): Flow<Result<BookData>> = callbackFlow {
        try {
            val dir = File(context.filesDir, "books")
            if (!dir.exists()) {
                dir.mkdir()  // creates if not exist
            }
            Log.d(TAG, "downloadBook: directory ${dir.path}")

            if (File(data.reference).exists()) {
                trySendBlocking(Result.success(data))
            } else {
                storage
                    .getReferenceFromUrl(data.bookUrl)
                    .getFile(File(data.reference))
                    .addOnSuccessListener {
                        data.saved = true
                        dao.updateBook(data.toBookEntity())
                        trySendBlocking(Result.success(data))
                    }
                    .addOnFailureListener {
                        trySendBlocking(Result.failure(it))
                    }
            }
        } catch (e: Exception) {
            Log.e("AAA", "downloadBook: ${e.printStackTrace()}", )
        }
        awaitClose()
    }
}