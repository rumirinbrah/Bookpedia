package com.plcoding.bookpedia.feature_book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import org.jetbrains.compose.resources.painterResource

@Composable
fun BlurredImageBackground(
    imageUrl: String? ,
    isFavourite: Boolean ,
    onFavouriteClick: () -> Unit ,
    onBack: () -> Unit ,
    modifier: Modifier = Modifier ,
    content: @Composable () -> Unit
) {
    var imageLoader by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl ,
        onSuccess = {
            imageLoader = if (it.painter.intrinsicSize.width > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size!"))
            }
        } ,
        onError = {
            it.result.throwable.printStackTrace()
            imageLoader = Result.failure(it.result.throwable)
        }
    )

    Box() {


        Column(
            modifier.fillMaxSize()
        ) {
            //blur box
            Box(
                Modifier.weight(0.3f)
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                imageLoader?.getOrNull()?.let { painter ->

                    Image(
                        painter = painter ,
                        contentDescription = "Book cover" ,
                        contentScale = ContentScale.Crop ,
                        modifier = Modifier.fillMaxSize()
                            .blur(10.dp)
                    )

                }
            }
            Box(
                Modifier.weight(0.7f)
                    .fillMaxWidth()
                    .background(DesertWhite)
            ) {

            }
        }
        IconButton(
            modifier = Modifier.statusBarsPadding()
                .align(Alignment.TopStart)
                .padding(top = 15.dp , start = 15.dp)
                .background(Color.Gray.copy(alpha = 0.5f) , CircleShape) ,
            onClick = { onBack() } ,
        ) {
            Icon(
                tint = DesertWhite ,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack ,
                contentDescription = "go back"
            )
        }
        Column(
            Modifier.fillMaxWidth() ,
            horizontalAlignment = Alignment.CenterHorizontally ,
        ) {
            Spacer(Modifier.fillMaxHeight(0.15f))

            //CARD
            ElevatedCard(
                onClick = {} ,
                modifier = Modifier.height(200.dp)
                    .aspectRatio(0.65f) ,
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.Transparent
                ) ,
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp) ,
                enabled = false
            ) {
                AnimatedContent(targetState = imageLoader) { result ->
                    when (result) {
                        null -> {
                            Box(
                                Modifier.fillMaxSize() ,
                                contentAlignment = Alignment.Center
                            ) {
                                PulseAnimation(Modifier.size(60.dp))
                            }

                        }

                        else -> {
                            Box(
                                Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = if (result.isSuccess) {
                                        painter
                                    } else {
                                        painterResource(Res.drawable.book_error_2)
                                    } ,
                                    contentDescription = "" ,
                                    modifier = Modifier.fillMaxSize() ,
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = { onFavouriteClick() } ,
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    Color.DarkGray ,
                                                    Color.Transparent
                                                ) ,
                                                radius = 50f
                                            ) ,
                                            shape = CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite ,
                                        contentDescription = "add/remove to favorite" ,
                                        tint = if (isFavourite) Color.Red else Color.White
                                    )
                                }
                            }

                        }
                    }
                }
            }
            content()
        }
    }


}