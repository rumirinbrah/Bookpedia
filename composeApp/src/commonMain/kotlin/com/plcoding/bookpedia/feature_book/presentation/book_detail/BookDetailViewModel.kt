package com.plcoding.bookpedia.feature_book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.navigation.Screen
import com.plcoding.bookpedia.feature_book.domain.DataSource
import com.plcoding.bookpedia.feature_book.domain.FavouriteBookRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.truncate

class BookDetailViewModel(
    private val favouriteBookRepo: FavouriteBookRepo ,
    private val dataSource: DataSource
) : ViewModel() {

    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState.onStart {
        getDescription()
        observeFavouriteState()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        _bookDetailState.value
    )

    init {
        //observeFavouriteState()
    }


    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> {
                _bookDetailState.update {
                    it.copy(book = null, isLoading = true)
                }

            }

            BookDetailAction.OnFavouriteClick -> {
                viewModelScope.launch {
                    if(_bookDetailState.value.isFavourite){
                        favouriteBookRepo.removeFromFavourite(_bookDetailState.value.book!!.id)
                    }else{
                        _bookDetailState.value.book?.let {
                            favouriteBookRepo.addToFavourite(it)
                        }
                    }
                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetailState.update {
                    it.copy(book = action.book)
                }
            }
        }
    }

    private fun getDescription() {
        viewModelScope.launch {

            _bookDetailState.value.book?.let {book->
                println("fetching desc for ${book.title}")
                dataSource.getBookDescription(book.id)
                    .onSuccess { desc ->
                        _bookDetailState.update {
                            it.copy(
                                book = it.book?.copy(description = desc.description ?: "Not found") ,
                                isLoading = false
                            )
                        }
                    }
                    .onError {
                        _bookDetailState.update {
                            it.copy(isLoading = false)
                        }
                    }
            }
        }
    }

    private fun observeFavouriteState() {
        _bookDetailState.value.book?.let {
            favouriteBookRepo.isBookFavourite(it.id)
                .onEach { isFav->
                    _bookDetailState.update {state->
                        state.copy(isFavourite = isFav)
                    }
                    println("Current fav state is $isFav")
                }.launchIn(viewModelScope)
        }
    }






}