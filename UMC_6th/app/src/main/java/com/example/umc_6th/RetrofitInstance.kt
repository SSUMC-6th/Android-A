package com.example.umc_6th

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(ApiRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val authApi = retrofit.create(AuthApi::class.java)
        val songApi = retrofit.create(SongApi::class.java)
        val albumApi = retrofit.create(AlbumApi::class.java)
    }
}