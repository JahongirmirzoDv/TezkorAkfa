package uz.algorithmgateway.tezkorakfa.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import javax.inject.Singleton


@Module
class ApplicationModule(var myApplication: MyApplication) {

    @Provides
    @Singleton
    fun provideApp(): MyApplication = myApplication


    @Provides
    @Singleton
    fun provideContext(): Context = myApplication.applicationContext

}