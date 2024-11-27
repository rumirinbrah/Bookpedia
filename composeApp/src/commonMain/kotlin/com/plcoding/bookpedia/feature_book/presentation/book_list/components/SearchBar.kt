package com.plcoding.bookpedia.feature_book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.search_hint
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery : String,
    onSearchQueryChange : (String) ->Unit,
    onImeSearch : ()->Unit
) {
    /*
    To change local selection colors
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ){}
     */



    OutlinedTextField(
        modifier = modifier.minimumInteractiveComponentSize()
            .background(DesertWhite, RoundedCornerShape(35)),
        value = searchQuery ,
        onValueChange = onSearchQueryChange,
        shape = RoundedCornerShape(35),
        textStyle = TextStyle(
            //fontSize = 15.sp
        ),
        placeholder = { Text(stringResource(Res.string.search_hint) , color = Color.Gray)  },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search ,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SandYellow,
            cursorColor = DarkBlue,
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {onImeSearch()}
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ) ,
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ){
                IconButton(
                    onClick = {
                        onSearchQueryChange("")
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.Close ,
                        contentDescription = null
                    )
                }

            }

        }
    )

}
