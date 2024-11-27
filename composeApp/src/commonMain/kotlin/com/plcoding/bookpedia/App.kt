package com.plcoding.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.core.data.networking.HttpClientFactory
import com.plcoding.bookpedia.core.navigation.Navigation
import com.plcoding.bookpedia.feature_book.data.networking.KtorDataSource
import com.plcoding.bookpedia.feature_book.domain.DataSource
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListViewModel
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        val bookListViewModel = koinViewModel<BookListViewModel>()
        Navigation(navController = navController, bookListViewModel = bookListViewModel)

    }
}