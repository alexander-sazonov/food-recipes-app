package com.alexandersazonov.foodrecipesapp.di

import com.alexandersazonov.foodrecipesapp.data.remote.RecipesService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val API_KEY = "f7f4c14569dc435493c926e2906a61b8"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRecipeService(): RecipesService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()
                val requestBuilder = chain.request().newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            }.build()
        val builder = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
        return builder.build().create(RecipesService::class.java)

    }
}