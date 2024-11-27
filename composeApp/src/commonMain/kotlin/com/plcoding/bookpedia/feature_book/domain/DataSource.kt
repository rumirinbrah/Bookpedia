package com.plcoding.bookpedia.feature_book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.feature_book.data.networking.dto.BookDescriptionDto

interface DataSource {

    suspend fun getSearchResult(query:String,limit : Int? = null) : Result<List<Book>,DataError.NetworkError>

    suspend fun getBookDescription(id : String) : Result<BookDescriptionDto,DataError.NetworkError>


}