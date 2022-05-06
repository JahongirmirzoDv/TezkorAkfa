package uz.algorithmgateway.tezkorakfa.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import javax.inject.Singleton
import androidx.datastore.preferences.createDataStore


@Module
class ApplicationModule(var myApplication: MyApplication) {

    @Provides
    @Singleton
    fun provideApp(): MyApplication = myApplication


    @Provides
    @Singleton
    fun provideContext(): Context = myApplication.applicationContext

}