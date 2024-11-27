package com.plcoding.bookpedia.feature_book.data.networking

import com.plcoding.bookpedia.core.data.networking.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import com.plcoding.bookpedia.feature_book.data.database.BookDao
import com.plcoding.bookpedia.feature_book.data.networking.dto.BookDescriptionDto
import com.plcoding.bookpedia.feature_book.data.networking.dto.SearchResponseDto
import com.plcoding.bookpedia.feature_book.data.toBook
import com.plcoding.bookpedia.feature_book.domain.Book
import com.plcoding.bookpedia.feature_book.domain.DataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorDataSource(
    private val httpClient: HttpClient,
    private val bookDao: BookDao
) : DataSource {

    override suspend fun getSearchResult(
        query: String ,
        limit: Int?
    ): Result<List<Book> , DataError.NetworkError> {
        return safeCall<SearchResponseDto> {
            httpClient.get("$BASE_URL/search.json") {
                parameter("q" , query)
                parameter("limit",limit)
                parameter("language","eng")
                parameter(
                    "fields" ,
                    "key,title,language,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
                )
            }
        }.map { response->
            response.books.map {
                it.toBook()
            }
        }
    }

    override suspend fun getBookDescription(id: String): Result<BookDescriptionDto , DataError.NetworkError> {
        val localResult = bookDao.getBookById(id)
        return if(localResult==null) {
            safeCall<BookDescriptionDto> {
                httpClient.get("$BASE_URL/works/$id.json")
            }
        }else{
            Result.Success(BookDescriptionDto(description = localResult.description))
        }
    }
}