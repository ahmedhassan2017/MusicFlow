package com.example.musicflow.data

import com.example.musicflow.pojo.MusicDataModel
import com.example.musicflow.pojo.TokenModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MusicClient private constructor() {
    private val musicInterface: MusicInterface
    fun getMusic(
        query: String?,
        token: String?
    ): Observable<List<MusicDataModel>?>? {
        return musicInterface.getMusic(query, 20, token)
    }

    fun getToken(): Observable<TokenModel?> {
        return musicInterface.token!!
    }

    companion object {
        private const val BASE_URL = "https://staging-gateway.mondiamedia.com/"
        var iNSTANCE: MusicClient? = null
            get() {
                if (null == field) field =
                    MusicClient()
                return field
            }
            private set
    }

    init {
        // because of late response or slow internet
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
        musicInterface = retrofit.create(MusicInterface::class.java)
    }
}