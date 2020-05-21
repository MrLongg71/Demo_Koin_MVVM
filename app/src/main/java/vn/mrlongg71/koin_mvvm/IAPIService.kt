package vn.mrlongg71.koin_mvvm

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IAPIService {
        @GET("list")
        fun getBooks(): Call<Book>
}