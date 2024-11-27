package com.plcoding.bookpedia.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailAction
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailScreenRoot
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailState
import com.plcoding.bookpedia.feature_book.presentation.book_detail.BookDetailViewModel
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(
    navController: NavHostController ,
    bookListViewModel: BookListViewModel
) {
    val bookDetailViewModel = koinViewModel<BookDetailViewModel>()
    //val bookDetailState by bookDetailViewModel.bookDetailState.collectAsStateWithLifecycle()


    NavHost(
        navController = navController ,
        startDestination = Screen.BookListScreen
    ) {
        composable<Screen.BookListScreen> {
            BookListScreenRoot(
                bookListViewModel = bookListViewModel ,
                onBookClick = { book ->
                    bookDetailViewModel.onAction(BookDetailAction.OnSelectedBookChange(book))
                    navController.navigate(Screen.BookDetailScreen(id = book.id))
                }
            )
        }

        composable<Screen.BookDetailScreen> {
            BookDetailScreenRoot(
                bookDetailViewModel = bookDetailViewModel,
                onBack = {
                    navController.navigateUp()
                }
            )

        }

    }

}