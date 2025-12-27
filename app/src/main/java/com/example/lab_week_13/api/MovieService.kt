package com.example.lab_week_13.api

import com.example.lab_week_13.model.PopularMoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun getInstance(): MovieService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}