package com.plcoding.bookpedia.feature_book.presentation.book_list

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.toUiText
import com.plcoding.bookpedia.feature_book.domain.Book
import com.plcoding.bookpedia.feature_book.domain.DataSource
import com.plcoding.bookpedia.feature_book.domain.FavouriteBookRepo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookListViewModel(
    private val dataSource: DataSource ,
    private val favouriteBookRepo: FavouriteBookRepo
) : ViewModel() {

    private val _bookListState = MutableStateFlow<BookListState>(BookListState(searchQuery = "harry"))
    val bookListState = _bookListState.onStart {
        if (cachedBooks.isEmpty()) {
            observeQueryChanges()
        }
        getFavouriteBooks()
    }.stateIn(
        viewModelScope ,
        SharingStarted.WhileSubscribed(5000) ,
        _bookListState.value
    )

    private var cachedBooks: List<Book> = emptyList()

    private var searchJob: Job? = null

    private var favouriteJob: Job? = null

    init {

    }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {
                //navigate
            }

            is BookListAction.OnSearchQuery -> {
                //search
                _bookListState.update {
                    it.copy(searchQuery = action.query)
                }

            }

            is BookListAction.OnTabChange -> {
                //change tab
                _bookListState.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeQueryChanges() {
        _bookListState
            .map {
                it.searchQuery
            }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _bookListState.update {
                            it.copy(
                                errorMessage = null ,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    query.length > 3 -> {
                        searchJob?.cancel()
                        searchJob = getSearchResults(query)
                    }
                }
            }.launchIn(viewModelScope)
        //explicitly launch in VM scope
    }

    private fun getSearchResults(query: String) =

        viewModelScope.launch {
            _bookListState.update {
                it.copy(
                    isLoading = true
                )
            }

            dataSource.getSearchResult(query , 15)
                .onSuccess { books ->
                    _bookListState.update {
                        it.copy(
                            isLoading = false ,
                            searchResult = books ,
                            errorMessage = null
                        )
                    }
                    cachedBooks = books
                }.onError { error ->
                    _bookListState.update {
                        it.copy(
                            isLoading = false ,
                            searchResult = emptyList() ,
                            errorMessage = error.toUiText()
                        )
                    }
                    //send error
                }
        }

    private fun getFavouriteBooks() {

        favouriteJob?.cancel()
        favouriteJob = favouriteBookRepo.getBooks().onEach { books ->
            _bookListState.update {
                it.copy(favouriteBooks = books)
            }
        }.launchIn(viewModelScope)

    }


}

@Composable
fun IDK(modifier: Modifier = Modifier) {
    TextField(
        modifier = Modifier.clip(RectangleShape) ,
        value = "" ,
        onValueChange = {} ,
        shape = RectangleShape
    )
}


