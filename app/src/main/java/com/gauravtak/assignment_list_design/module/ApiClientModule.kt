package com.assignment.listdesign.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//this is used to provide the retrofit api client module to call apis
@Module
class ApiClientModule(private val BASE_URL: String) {
    @get:Singleton
    @get:Provides
    val client: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(httpLoggingInterceptor))
                .build()

    private fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }
// this http logging interceptor is added to see the logs over the api calls based on request and response,
// for production or release this log or interceptor can be removed
private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

}
