package com.banap.banap.core.network

import com.banap.banap.common.Constants.AUTH_URL
import com.banap.banap.common.Constants.LOCAL_URL
import com.banap.banap.common.Constants.PRODUCER_URL
import com.banap.banap.common.Constants.WEATHER_URL
import com.banap.banap.core.data.local.token.TokenManager
import com.banap.banap.data.remote.login.LoginService
import com.banap.banap.data.remote.producer.ProducerService
import com.banap.banap.data.remote.property.PropertyService
import com.banap.banap.data.remote.weather.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = tokenManager.getToken("token")

                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")

                token?.let {
                    request.addHeader("Authorization", it)
                }

                chain.proceed(request.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    @Named("AUTH_RETROFIT")
    fun provideAuthRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("WEATHER_RETROFIT")
    fun provideWeatherRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("PRODUCER_RETROFIT")
    fun provideProducerRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(PRODUCER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideLoginService(
        @Named("AUTH_RETROFIT") retrofit: Retrofit
    ): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideWeatherService(
        @Named("WEATHER_RETROFIT") retrofit: Retrofit
    ): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideProducerService(
        @Named("PRODUCER_RETROFIT") retrofit: Retrofit
    ): ProducerService =
        retrofit.create(ProducerService::class.java)

    @Provides
    @Singleton
    fun providePropertyService(
        @Named("PRODUCER_RETROFIT") retrofit: Retrofit
    ): PropertyService =
        retrofit.create(PropertyService::class.java)
}