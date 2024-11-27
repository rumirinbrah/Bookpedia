package com.plcoding.bookpedia.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(engine: HttpClientEngine) : HttpClient{
        return HttpClient(engine){
            //debugging
            install(Logging){
                level=LogLevel.ALL
                logger = object : Logger{
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
            install(ContentNegotiation){
                //for kotlinx serialization
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout){
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

}