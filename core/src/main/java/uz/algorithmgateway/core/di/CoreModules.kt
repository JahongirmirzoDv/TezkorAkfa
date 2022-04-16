package uz.algorithmgateway.core.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.algorithmgateway.core.ConfigService
import uz.algorithmgateway.core.ConfigServiceImpl
import uz.algorithmgateway.data.api.service.ApiService
import uz.algorithmgateway.data.api.service.ApiServiceImpl
import uz.algorithmgateway.data.api.service.CoreApi
import java.util.concurrent.TimeUnit

/**
 * Created by Abrorjon Berdiyorov on 23.03.2022
 */


val configModule = module {

    single<ConfigService> { ConfigServiceImpl(get(), ConfigService.CONFIG_SERVICE_TAG) }
}

val apiModule = module {

    single<ApiService> { ApiServiceImpl(get(), get()) }

    factory { provideApi(get(), get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideGson() }
}

fun provideGson(): Gson = Gson()

fun provideOkHttpClient (configService: ConfigService): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Token ${configService.getAccessToken()}")
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}

fun provideApi(gson: Gson, client: OkHttpClient): CoreApi {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://164.92.242.166:1337/api/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(CoreApi::class.java)
}