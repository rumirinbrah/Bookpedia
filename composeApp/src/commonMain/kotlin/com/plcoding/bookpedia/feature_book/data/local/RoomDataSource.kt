package com.plcoding.bookpedia.feature_book.data.local

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.feature_book.data.database.BookDao
import com.plcoding.bookpedia.feature_book.data.toBook
import com.plcoding.bookpedia.feature_book.data.toBookEntity
import com.plcoding.bookpedia.feature_book.domain.Book
import com.plcoding.bookpedia.feature_book.domain.FavouriteBookRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(
    private val bookDao: BookDao
) : FavouriteBookRepo {

    override fun getBooks(): Flow<List<Book>> {
        return bookDao.getBooks().map {list->
            list.map {
                it.toBook()
            }
        }
    }

    override fun isBookFavourite(id: String): Flow<Boolean> {
        return bookDao.getBooks()
            .map {list->
                list.any { it.id == id }
            }
    }

    override suspend fun addToFavourite(book: Book): EmptyResult<DataError.LocalError> {
        return try {
            bookDao.addBook(book.toBookEntity())
            Result.Success(Unit)
        }catch (e : SQLiteException){
            Result.Error(DataError.LocalError.DISK_FULL)
        }
    }

    override suspend fun removeFromFavourite(id: String) {
        bookDao.removeFromFav(id)
    }

    override suspend fun getBookById(id: String) : Book? {
        return bookDao.getBookById(id)?.toBook()
    }
}