package com.example.gitem.di

import com.example.gitem.BuildConfig
import com.example.gitem.BuildConfig.API_KEY
import com.example.gitem.BuildConfig.HOST_NAME
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.MainGithubDataSource
import com.example.gitem.data.remote.ApiService
import com.example.gitem.data.repo.GitemRepository
import com.example.gitem.data.repo.MainGitemRepository
import com.example.gitem.utils.NetworkExceptionInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(NetworkExceptionInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor =
        Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
            chain.proceed(requestBuilder.build())
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(HOST_NAME)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesGithubDataSource(
        apiService: ApiService
    ): GithubDataSource {
        return MainGithubDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(githubDataSource: GithubDataSource): GitemRepository =
        MainGitemRepository(githubDataSource)
}