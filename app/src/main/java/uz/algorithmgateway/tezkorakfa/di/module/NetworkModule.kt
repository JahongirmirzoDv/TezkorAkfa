package uz.algorithmgateway.tezkorakfa.di.module

import com.mocklets.pluto.BuildConfig
import com.mocklets.pluto.PlutoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.algorithmgateway.data.retrofit.ApiService
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getApi(): String = "http://164.92.242.166:1337/api/v1/"

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return okHttpClient.addInterceptor(httpLoggingInterceptor)
            .addInterceptor(PlutoInterceptor())
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Token 5c883bec5202c1cb2a8c7e7f5968898053f5b95c")
                    .build()
                chain.proceed(newRequest)
            }
        .build()
    }


    @Singleton
    @Provides
    fun provideRetforit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}