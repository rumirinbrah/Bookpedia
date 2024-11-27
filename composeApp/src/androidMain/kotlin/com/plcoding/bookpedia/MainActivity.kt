package com.plcoding.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.feature_book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.feature_book.presentation.book_list.components.SearchBar
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    Box(Modifier.size(100.dp),
        contentAlignment = Alignment.Center){
        PulseAnimation(Modifier.size(60.dp))
        Text("mf")
    }

}