package com.plcoding.bookpedia.feature_book.presentation.book_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.LightBlue
import com.plcoding.bookpedia.core.presentation.PulseAnimation
import com.plcoding.bookpedia.core.presentation.SandYellow
import com.plcoding.bookpedia.feature_book.domain.Book
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    book: Book ,
    onClick: () -> Unit ,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier.clickable(onClick = onClick) ,
        color = LightBlue.copy(alpha = 0.2f) ,
        shape = RoundedCornerShape(25)
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(8.dp)
                .height(IntrinsicSize.Min) , //take the ele with highest height,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.height(100.dp) ,
                contentAlignment = Alignment.Center
            ) {
                var imageLoadingResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl ,
                    onSuccess = {
                        //sometimes the API return an image of 0 dimens. when error occurs
                        imageLoadingResult = if (it.painter.intrinsicSize.width > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadingResult = Result.failure(it.result.throwable)
                    }
                )
                val painterState by painter.state.collectAsStateWithLifecycle()
                val transition  by animateFloatAsState(
                    targetValue = if(painterState is AsyncImagePainter.State.Success){
                        1f
                    }else{
                        0f
                    },
                    animationSpec = tween(500)
                )

                when(val result = imageLoadingResult){
                   null -> {
                       Row(
                           Modifier.width(65.dp),
                           horizontalArrangement = Arrangement.Center
                       ) {
                           PulseAnimation(Modifier.size(60.dp))
                       }

                   }
                   else->{
                       Image(
                           painter = if(result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                           contentDescription = book.title,
                           contentScale = if(result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                           modifier = Modifier.aspectRatio(ratio=0.65f,matchHeightConstraintsFirst = true)
                               .graphicsLayer {
                                   rotationX= (1f-transition)*30f
                                   val scale = 0.8f + (0.2f * transition)
                                   scaleX = scale
                                   scaleY = scale
                               }
                       )
                   }

                }


            }
            Column(
                Modifier.padding(start=5.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = book.title ,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.firstOrNull() ?: "Unknown" ,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                book.averageRating?.let {avg->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //rounding function - e.g. 7.666 ; round(7.666*10) = 77 ; rating = 7.7
                        val rating = round(avg*10)/10.0
                        Text(
                            text = "$rating" ,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "rating star",
                            tint = SandYellow
                        )
                    }
                }


            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go to book"
            )
        }
    }


}