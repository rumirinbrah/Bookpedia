package com.plcoding.bookpedia.feature_book.data

import com.plcoding.bookpedia.feature_book.data.database.BookEntity
import com.plcoding.bookpedia.feature_book.data.networking.dto.SearchedBookDto
import com.plcoding.bookpedia.feature_book.domain.Book

fun SearchedBookDto.toBook() : Book{
     return Book(
         id = id.substringAfterLast("/") ,
         title = title  ,
         imageUrl = if(coverKey!=null){
             "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
         }else{
             "https://covers.openlibrary.org/b/id/${coverAltKey}-L.jpg"
         },
         authors =  authorNames ?: emptyList() ,
         description = null ,
         languages =  languages ?: emptyList() ,
         firstPublishYear =  firstPublishYear.toString(),
         averageRating =  ratingsAverage,
         ratingCount =  ratingsCount,
         numPages =  numPages ?: 0,
         numEditions = numEditions ?: 0
     )
}

fun Book.toBookEntity() : BookEntity{
    return BookEntity(
        id =  id,
        title = title ,
        description = description ,
        imageUrl =  imageUrl,
        languages =  languages,
        authors = authors ,
        firstPublishYear = firstPublishYear  ,
        averageRating =  averageRating,
        ratingCount =  ratingCount,
        numPages =  numPages,
        numEdition = numEditions
    )
}

fun BookEntity.toBook() : Book{
    return Book(
        id = id ,
        title = title  ,
        imageUrl =  imageUrl,
        authors = authors ,
        description =  description,
        languages = languages ,
        firstPublishYear =  firstPublishYear,
        averageRating =  averageRating,
        ratingCount = ratingCount ,
        numPages =  numPages ,
        numEditions = numEdition
    )
}


