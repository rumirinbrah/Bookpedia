package com.plcoding.bookpedia.feature_book.presentation.book_list

import com.plcoding.bookpedia.core.presentation.UiText
import com.plcoding.bookpedia.feature_book.domain.Book

data class BookListState(
    val searchQuery : String = "" ,
    val searchResult : List<Book> = emptyList() ,
    val favouriteBooks : List<Book> = emptyList() ,
    val isLoading : Boolean = false ,
    val selectedTabIndex : Int = 0 ,
    val errorMessage : UiText? = null

)
