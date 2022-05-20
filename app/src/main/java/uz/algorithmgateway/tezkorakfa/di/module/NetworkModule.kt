package uz.algorithmgateway.tezkorakfa.di.module

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mocklets.pluto.PlutoInterceptor
import dagger.Module
import dagger.Provides
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.algorithmgateway.tezkorakfa.BuildConfig
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.MontageService
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.Servicer
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.SupplierService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getApi(): String = BuildConfig.BASE_URL

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
            .addInterceptor(ChuckerInterceptor.Builder(MyApplication.instance).build())
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Token eb35b8a8f7b61d0267cfa22c3cd5da6290d36e60")
                    .build()
                chain.proceed(newRequest)
            }
            .connectTimeout(5, TimeUnit.MINUTES)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetforit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMontageService(retrofit: Retrofit): MontageService =
        retrofit.create(MontageService::class.java)

    @Singleton
    @Provides
    fun provideServicer(retrofit: Retrofit): Servicer = retrofit.create(Servicer::class.java)

    @Singleton
    @Provides
    fun provideSupplierService(retrofit: Retrofit): SupplierService =
        retrofit.create(SupplierService::class.java)
}