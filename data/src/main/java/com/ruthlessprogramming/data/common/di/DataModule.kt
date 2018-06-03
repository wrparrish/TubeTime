package com.ruthlessprogramming.data.common.di

import com.google.gson.Gson
import com.ruthlessprogramming.data.BuildConfig
import com.ruthlessprogramming.data.datasources.StopRetrofitDataSource
import com.ruthlessprogramming.data.services.StopService
import com.ruthlessprogramming.domain.datasource.StopDataSource
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val paramId = "app_id"
const val paramKey = "app_key"
const val tflId = "45e66ed2"
const val tflKey = "573176c0b62bfb25f7a5cdbdd2095348"

@Module
class DataModule {

    private val baseUrl = "https://api.tfl.gov.uk/"
    private val timeoutMs = 15_000L


    @Singleton
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
            .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
            .addInterceptor(TokenInterceptor())
            .addInterceptor(getCommonLogging())
            .build()
    }


    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun proviesStopService(retrofit: Retrofit): StopService {
        return retrofit.create(StopService::class.java)
    }

    @Singleton
    @Provides
    fun providesStopDataSource(api: StopService): StopDataSource {
        return StopRetrofitDataSource(api)
    }


}

private fun getCommonLogging(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    val isLoggingEnabled = BuildConfig.DEBUG
    val logLevel = if (isLoggingEnabled) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
    logging.level = logLevel
    return logging
}


class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originUrl = originalRequest.url()

        val amendedUrl = originUrl.newBuilder()
            .addQueryParameter(paramId, tflId)
            .addQueryParameter(paramKey, tflKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(amendedUrl)
            .build()

        return chain.proceed(newRequest)
    }
}