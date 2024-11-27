package com.plcoding.bookpedia.feature_book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteBookRepo {

    fun getBooks() : Flow<List<Book>>

    fun isBookFavourite(id : String) : Flow<Boolean>

    suspend fun addToFavourite(book: Book) : EmptyResult<DataError.LocalError>

    suspend fun removeFromFavourite(id : String)

    suspend fun getBookById(id :String) : Book?
}