package vn.mrlongg71.koin_mvvm

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

val viewModelModule = module {
    viewModel {
        BookViewModel(get())
    }
}

val repositoryModule = module {
    single {
        BookRepository(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): IAPIService {
        return retrofit.create(IAPIService::class.java)
    }

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlODAyMWFkYzk1M2YxMGM5NjRhMWE1ZCIsImVtYWlsIjoibXJsb25nZzcxQGdtYWlsLmNvbSJ9LCJpYXQiOjE1ODU0OTIwNDd9.US1PsUeIB6bibCImRnj4i-gQn84Ly-0vX5o4csrLkXw") // <-- this is the important line

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://vn-food.herokuapp.com/api/products/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

