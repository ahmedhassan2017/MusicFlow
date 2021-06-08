package com.example.musicflow.data

import com.example.musicflow.pojo.MusicDataModel
import com.example.musicflow.pojo.TokenModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface MusicInterface {
    @get:POST("v0/api/gateway/token/client")
    @get:Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json",
        "X-MM-GATEWAY-KEY: Ge6c853cf-5593-a196-efdb-e3fd7b881eca"
    )
    val token: Observable<TokenModel?>?

    @Headers(
        "Accept: application/json",
        "X-MM-GATEWAY-KEY: Ge6c853cf-5593-a196-efdb-e3fd7b881eca"
    )
    @GET("v2/api/sayt/flat")
    fun getMusic(
        @Query("query") query: String?,
        @Query("limit") limit: Int,
        @Header("Authorization") token: String?
    ): Observable<List<MusicDataModel>?>?
}