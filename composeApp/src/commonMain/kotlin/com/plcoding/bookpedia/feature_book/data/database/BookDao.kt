package com.plcoding.bookpedia.feature_book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BookDao {

    @Upsert
    abstract suspend fun addBook(bookEntity: BookEntity)

    @Query("select * from book_table")
    abstract fun getBooks() : Flow<List<BookEntity>>

    @Query("select * from book_table where id = :id")
    abstract suspend fun getBookById(id : String) :BookEntity?

    @Query("delete from book_table where id = :id")
    abstract suspend fun removeFromFav(id : String)

}