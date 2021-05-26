package com.arulvakku.lyrics.app.data.api

import com.arulvakku.lyrics.app.data.model.Song
import com.arulvakku.lyrics.app.data.model.SongCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("GetSongsCategoryList")
    suspend fun getSongCategories(): Response<SongCategory>

    @Headers("Content-Type: application/json")
    @GET("GetSongsList")
    suspend fun getSongs(): Response<Song>
}