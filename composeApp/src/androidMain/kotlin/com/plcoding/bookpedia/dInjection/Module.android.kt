package com.plcoding.bookpedia.dInjection

import com.plcoding.bookpedia.feature_book.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> {
            OkHttp.create()
        }
        single { DatabaseFactory(androidApplication()) }
    }

/*
val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")

        val appDir = when{
            os.contains("win")->{
                File(System.getenv("APPDATA"),"Bookpedia")
            }
            os.contains("mac")->{
                File(userHome,"Library/Application Support/Bookpedia")
            }
            else->{
                File(userHome,".local/share/Bookpedia")
            }
        }
        if(!appDir.exists()){
            appDir.mkdirs()
        }
        val dbFile = File(appDir,BookDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
 */