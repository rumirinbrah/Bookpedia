package com.plcoding.bookpedia.feature_book.data.networking.dto

import com.plcoding.bookpedia.feature_book.domain.Book
import io.ktor.util.valuesOf
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object BookDescDtoSerializer : KSerializer<BookDescriptionDto> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookDescriptionDto::class.simpleName!!
    ) {
        element<String>("description")
    }

    override fun deserialize(decoder: Decoder): BookDescriptionDto =
        decoder.decodeStructure(descriptor)
        {
            var description: String? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    //index is the index of eles we got in descriptor
                    0 -> {
                        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Only works with JSON")
                        val element = jsonDecoder.decodeJsonElement()
                        description = if(element is JsonObject){
                            decoder.json.decodeFromJsonElement<JsonDescriptionDto>(
                                element=element,
                                deserializer = JsonDescriptionDto.serializer()
                            ).value
                        }else if(element is JsonPrimitive && element.isString){
                            element.content
                        }else{
                            null
                        }
                    }
                    CompositeDecoder.DECODE_DONE->{
                        break
                    }
                    else-> throw SerializationException("Unexpected index $index")
                }
            }
            return@decodeStructure BookDescriptionDto(description = description)
        }

    override fun serialize(encoder: Encoder , value: BookDescriptionDto)= encoder.encodeStructure(
        descriptor
    ) {
        value.description?.let {
            encodeStringElement(descriptor,0,it)
        }
    }
}