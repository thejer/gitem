package com.example.gitem.di

import com.example.gitem.BuildConfig
import com.example.gitem.BuildConfig.HOST_NAME
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.MainGithubDataSource
import com.example.gitem.data.remote.ApiService
import com.example.gitem.data.repo.GithubRepository
import com.example.gitem.data.repo.MainGithubRepository
import com.example.gitem.utils.NetworkExceptionInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
//        paramInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(paramInterceptor)
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

//    @Provides
//    @Singleton
//    fun provideParamInterceptor(): Interceptor =
//        Interceptor { chain ->
//            val url = chain.request().url.newBuilder()
//                .addQueryParameter("apikey", API_KEY)
//                .build()
//            val requestBuilder = chain.request()
//                .newBuilder()
//                .url(url)
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }

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
    fun provideRepository(githubDataSource: GithubDataSource): GithubRepository =
        MainGithubRepository(githubDataSource)
}